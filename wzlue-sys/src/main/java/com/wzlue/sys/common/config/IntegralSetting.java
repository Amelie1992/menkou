package com.wzlue.sys.common.config;

import java.util.List;

public class IntegralSetting {
	

	private SignInRule signInRule;

	private NewbieTask newbieTask;

	public SignInRule getSignInRule() {
		return signInRule;
	}

	public void setSignInRule(SignInRule signInRule) {
		this.signInRule = signInRule;
	}

	public NewbieTask getNewbieTask() {
		return newbieTask;
	}

	public void setNewbieTask(NewbieTask newbieTask) {
		this.newbieTask = newbieTask;
	}

	public class NewbieTask{
		private Integer score;

		public Integer getScore() {
			return score;
		}

		public void setScore(Integer score) {
			this.score = score;
		}
	}

	
	public class SignInRule{

		private Integer score;

		private List<ContinuousAwardRule> awardRules;

		public Integer getScore() {
			return score;
		}

		public void setScore(Integer score) {
			this.score = score;
		}

		public List<ContinuousAwardRule> getAwardRules() {
			return awardRules;
		}

		public void setAwardRules(List<ContinuousAwardRule> awardRules) {
			this.awardRules = awardRules;
		}
	}


	public class ContinuousAwardRule{

		Integer days;
		Integer addscore;

		public Integer getDays() {
			return days;
		}

		public void setDays(Integer days) {
			this.days = days;
		}

		public Integer getAddscore() {
			return addscore;
		}

		public void setAddscore(Integer addscore) {
			this.addscore = addscore;
		}
	}
}
