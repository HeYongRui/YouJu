package com.heyongrui.base.dagger;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * 2019/8/26
 * lambert
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
