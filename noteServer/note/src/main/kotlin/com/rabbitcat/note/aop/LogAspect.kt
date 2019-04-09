package com.rabbitcat.note.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
@Aspect
class LogAspect {
    val logger = LoggerFactory.getLogger(javaClass)

    @Before("execution(* com.rabbitcat.note.controller.*.*.*(..))")
    fun controllerLogging(pjp: JoinPoint){
        logger.info("-------------------------------------")

        /* 전달되는 모든 파라미터들을 Object의 배열로 가져온다. */
        logger.info("1 파라미터:" + Arrays.toString(pjp.args))

        /* 해당 Advice의 타입을 알아낸다. */
        logger.info("2 Advice의 타입:" + pjp.kind)

        /* 실행하는 대상 객체의 메소드에 대한 정보를 알아낼 때 사용 */
        logger.info("3 객체의 메소드 이름:" + pjp.signature.name)

        /* target 객체를 알아낼 때 사용 */
        logger.info("4 target 객체:" + pjp.target.toString())

        /* Advice를 행하는 객체를 알아낼 때 사용 */
        //logger.info("5 Advice를 행하는 객체:" +pjp.`this`.toString())

        logger.info("-------------------------------------")
    }

    @Around("execution(* com.rabbitcat.note.controller.*.*.*(..))")
    fun controllerTimeLog(pjp: ProceedingJoinPoint): Any {
        val startTime = System.currentTimeMillis()

        //실제 타겟을 실행하는 부분이다. 이 부분이 없으면 advice가 적용된 메소드가 동작을 안할것 같다.
        val result = pjp.proceed()  //proceed는 Exception 보다 상위 Throwable을 처리해야 한다.
        logger.info("==============================")
        val endTime = System.currentTimeMillis()
        logger.info(pjp.signature.name + " 실행시간 : " + (endTime - startTime) + "ms")  //target 메소드의 동작 시간을 출력한다.
        logger.info("==============================")

        //Around를 사용할 경우 반드시 Object를 리턴해야 한다.
        return result
    }

    @Around("execution(* com.rabbitcat.note.service.fileStorage.FileStorageService.downloadFile(..))")
    fun fileDownloadLog(pjp: ProceedingJoinPoint): Any{
        val startTime = System.currentTimeMillis()

        val result = pjp.proceed()
        logger.info("==============================")
        val endTime = System.currentTimeMillis()
        logger.info(pjp.signature.name)
        logger.info("파라미터: " + Arrays.toString(pjp.args))
        logger.info("파일 다운로드 완료")
        logger.info("파일 다운로드 시간 : " + (endTime - startTime) + "ms")  //target 메소드의 동작 시간을 출력한다.
        logger.info("==============================")

        return result
    }

    @Around("execution(* com.rabbitcat.note.service.fileStorage.FileStorageService.uploadFile(..))")
    fun fileUploadLog(pjp: ProceedingJoinPoint): Any{
        val startTime = System.currentTimeMillis()

        val result = pjp.proceed()
        logger.info("==============================")
        val endTime = System.currentTimeMillis()
        logger.info(pjp.signature.name)
        logger.info("파라미터: " + Arrays.toString(pjp.args))
        logger.info("파일 업로드 완료")
        logger.info("업로드 시간 : " + (endTime - startTime) + "ms")  //target 메소드의 동작 시간을 출력한다.
        logger.info("==============================")

        return result
    }

    @Around("execution(* com.rabbitcat.note.service.fileStorage.FileStorageService.deleteFile(..))")
    fun fileDeleteLog(pjp: ProceedingJoinPoint): Any{
        val startTime = System.currentTimeMillis()

        val result = pjp.proceed()
        logger.info("==============================")
        val endTime = System.currentTimeMillis()
        logger.info(pjp.signature.name)
        logger.info("파라미터: " + Arrays.toString(pjp.args))
        logger.info("파일 삭제 완료")
        logger.info("삭제 시간 : " + (endTime - startTime) + "ms")  //target 메소드의 동작 시간을 출력한다.
        logger.info("==============================")

        return result
    }
}