package org.springframework.xiaofa;

/**
 * @desc:
 * @Author: zhaoxiaofa
 * @Date: 2019-09-03 20:49
 */
public class SpringHelloWord {

	private String testKey;

	private String testKey1;

	public String helloWorld(){

		return "hello world";
	}

	public String getTestKey() {
		return testKey;
	}

	public void setTestKey(String testKey) {
		this.testKey = testKey;
	}

	public String getTestKey1() {
		return testKey1;
	}

	public void setTestKey1(String testKey1) {
		this.testKey1 = testKey1;
	}
}
