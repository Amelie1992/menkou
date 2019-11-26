package com.wzlue.wechat.service.impl;

import com.google.common.collect.Lists;
import com.wzlue.common.base.Query;
import com.wzlue.common.exception.RRException;
import com.wzlue.wechat.dao.BalanceDao;
import com.wzlue.wechat.dao.BalanceRecordDao;
import com.wzlue.wechat.entity.BalanceEntity;
import com.wzlue.wechat.entity.BalanceRecordEntity;
import com.wzlue.wechat.service.BalanceService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @date 2019-05-21 下午 1:49
 */
@Service("balanceService")
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private BalanceDao balanceDao;

    @Autowired
    private BalanceRecordDao balanceRecordDao;


    @Override
    public BigDecimal getUserBalance(String userId) {
        if (userId == null) {
            throw new RRException("获取当前登录用户失败");
        }
        ;
        BigDecimal balance = balanceDao.queryBalanceByUserId(userId);
        if (balance == null) {
            initUserBalance(userId);
            return BigDecimal.ZERO;
        }
        return balance;
    }

    @Override
    public List<BalanceRecordEntity> getBalanceRecordList(Long userId, Query query) {
        if (userId == null) return Lists.newArrayList();
        List<BalanceRecordEntity> entityList = balanceRecordDao.queryByUserId(userId, query);
        if (CollectionUtils.isEmpty(entityList)) {
            return Lists.newArrayList();
        }
        return entityList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBalance(String userId, BigDecimal amount, Integer type, Long createBy) {
        //参数校验
        validateUpdateBalance(userId, amount, type);

        //查询锁版本号和余额
        BalanceEntity balanceEntity = balanceDao.queryByUserId(userId);
        //如果余额没查到，则初始化余额
        if (balanceEntity == null) {
            initUserBalance(userId);
            balanceEntity = balanceDao.queryByUserId(userId);
        }

        BigDecimal balance = balanceEntity.getBalance();
        Long version = balanceEntity.getVersion();

        if (type != null && type == 1) {
            BigDecimal targetBalance = balance.add(amount);
            //+余额
            int updateColumns = balanceDao.updateBalance(userId, targetBalance, version, new Date());
            if (updateColumns < 1) {
                throw new RRException("余额更新失败");
            } else {
                saveBalanceRecord(userId, amount, type,createBy);
            }
        } else if (type != null && type == 2) {
            //更新后的余额不能为负数
            BigDecimal targetBalance = balance.subtract(amount);
            if (targetBalance.compareTo(BigDecimal.ZERO) == -1) {
                throw new RRException("余额修改后不能为负数");
            }
            //-余额
            int updateColumns = balanceDao.updateBalance(userId, targetBalance, version, new Date());
            if (updateColumns < 1) {
                throw new RRException("余额更新失败");
            } else {
                saveBalanceRecord(userId, amount, type, createBy);
            }
        }
        return true;
    }

    /**
     * 更新余额校验
     *
     * @param userId 用户id
     * @param amount 更新的金额，必须大于0
     * @param type   类型
     */
    private void validateUpdateBalance(String userId, BigDecimal amount, Integer type) {
        if (StringUtils.isBlank(userId)) {
            throw new RRException("用户id不能为空");
        }
        //更新的量必须大于0
        if (amount == null) {
            throw new RRException("修改的金额不能为空");
        }
        if (amount.compareTo(BigDecimal.ZERO) == -1) {
            throw new RRException("修改的金额必须大于0");
        }
        if (amount.scale() > 2) {
            throw new RRException("金额最多精确到小数点后两位");
        }
        if (type == null || (type != 1 && type != 2)) {
            throw new RRException("更新类型不存在");
        }

    }

    /**
     * 初始化用户的余额
     *
     * @param userId
     */
    private void initUserBalance(String userId) {
        //没查到则新增一条，默认置为余额为0
        BalanceEntity newEntity = new BalanceEntity();
        newEntity.setUserId(userId);
        newEntity.setBalance(new BigDecimal(0));
        newEntity.setCreateDate(new Date());
        newEntity.setVersion(0L);
        balanceDao.save(newEntity);
    }

    /**
     * 插入余额记录
     *
     * @param userId
     * @param amount
     * @param type
     */
    private void saveBalanceRecord(String userId, BigDecimal amount, Integer type, Long createBy) {
        //余额更新成功,插入更新记录
        BalanceRecordEntity balanceRecordEntity = new BalanceRecordEntity();
        balanceRecordEntity.setUserId(userId);
        balanceRecordEntity.setAmount(amount);
        balanceRecordEntity.setType(type);
        balanceRecordEntity.setCreateBy(createBy);
        balanceRecordEntity.setCreateDate(new Date());
        balanceRecordEntity.setUpdateDate(new Date());
        balanceRecordDao.save(balanceRecordEntity);
    }


}
