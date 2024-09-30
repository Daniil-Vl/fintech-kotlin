package ru.tbank.springapp.aspect

import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.slf4j.LoggerFactory
import org.springframework.aop.framework.ProxyFactory
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component

@Component
class TimedPostProcessor : BeanPostProcessor {

    private val registeredBeans: MutableMap<String, Class<*>> = HashMap()

    @Throws(BeansException::class)
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        val javaClass: Class<*> = bean.javaClass

        if (javaClass.isAnnotationPresent(Timed::class.java))
            registeredBeans[beanName] = javaClass

        return super.postProcessBeforeInitialization(bean, beanName)
    }

    @Throws(BeansException::class)
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        registeredBeans[beanName] ?: return super.postProcessAfterInitialization(bean, beanName)

        val proxyFactory = ProxyFactory(bean)
        proxyFactory.addAdvice(TimedInterceptor())

        return proxyFactory.proxy
    }


    private class TimedInterceptor : MethodInterceptor {

        private val logger = LoggerFactory.getLogger(this::class.java)

        @Throws(Throwable::class)
        override fun invoke(invocation: MethodInvocation): Any? {
            val methodName = invocation.method.name
            val className = invocation.method.declaringClass.name

            logger.info("Method $methodName started...")

            try {
                val startTime = System.nanoTime()
                val result = invocation.proceed()
                val finishTime = System.nanoTime()

                logger.info(
                    "Method $className.$methodName took ${finishTime - startTime} nanoseconds"
                )

                return result
            } catch (exception: Exception) {
                logger.error("Failed to execute method $methodName on class $className")
                logger.error(exception.message)
                throw exception
            }
        }
    }
}
