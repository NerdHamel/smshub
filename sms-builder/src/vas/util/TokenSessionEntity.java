package vas.util;

import java.util.Date;

public class TokenSessionEntity {

	public String msisdn;
	public String token;
	public String expiry;
	public Date createdOn;
	public String sessionId;
	public String serviceAccount;
	public String packageId;

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public String lang;

	public String getServiceAccount() {
		return serviceAccount;
	}

	public void setServiceAccount(String serviceAccount) {
		this.serviceAccount = serviceAccount;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public TokenSessionEntity() {

	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getExpiry() {
		return expiry;
	}

	public boolean isActive() {

		if (token != null && token.trim().length() > 0 && new Date().getTime() - createdOn.getTime() < 30 * 60 * 1000)
			return true;

		return false;
	}

	//@Override
	public String toString() {
		return "TokenSessionEntity [msisdn=" + msisdn + ", token=" + token + ", expiry=" + expiry + ", createdOn="
				+ createdOn + ", sessionId=" + sessionId + ", serviceAccount=" + serviceAccount + ", packageId="
				+ packageId + ", lang=" + lang + "]";
	}

}
