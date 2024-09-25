package ru.tbank.springapp.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@Aspect
class TimedAspect {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Pointcut("@annotation(ru.tbank.springapp.aspect.Timed)")
    fun hasAnnotation() {
    }

    @Around("hasAnnotation()")
    @Throws(Exception::class)
    fun measureExecutionTime(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature

        val methodName = signature.method.name
        val targetClassName = joinPoint.target.javaClass.name

        logger.info("Catch $targetClassName.$methodName execution in TimedAspect")

        return try {
            val startTime = System.nanoTime()
            val result = joinPoint.proceed()
            val finishTime = System.nanoTime()

            logger.info(
                "Method $targetClassName.$methodName took ${finishTime - startTime} nanoseconds"
            )

            result
        } catch (exception: Exception) {
            logger.error("Failed to execute method $methodName on class $targetClassName")
            logger.error(exception.message)
            throw exception
        }
    }
}
