package org.springframework.xiaofa;

/**
 * @desc:
 * @Author: zhaoxiaofa
 * @Date: 2019-09-03 20:49
 */
public class SpringHelloWord {

	private String testKey;

	public String helloWorld(){

		return "hello world";
	}

	public String getTestKey() {
		return testKey;
	}

	public void setTestKey(String testKey) {
		this.testKey = testKey;
	}
}
