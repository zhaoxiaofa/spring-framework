package org.springframework.xiaofa;

/**
 * @desc:
 * @Author: zhaoxiaofa
 * @Date: 2019-09-06 14:42
 */
public abstract class AbstractLanguage {

	protected abstract Language getBean();

	public void sayHello(){
		getBean().sayHello();

	}

}
