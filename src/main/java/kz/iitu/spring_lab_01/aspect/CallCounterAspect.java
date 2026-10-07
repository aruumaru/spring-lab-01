package kz.iitu.spring_lab_01.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Aspect
@Component
public class CallCounterAspect {

    private final Map<String, AtomicLong> counterMap = new ConcurrentHashMap<>();

    @Before("kz.iitu.spring_lab_01.aspect.Pointcuts.serviceOperation()")
    public void countCall(JoinPoint jp) {
        String methodName = jp.getSignature().toShortString();
        counterMap.computeIfAbsent(methodName, k -> new AtomicLong(0)).incrementAndGet();
    }

    public Map<String, Long> getStats() {
        Map<String, Long> stats = new ConcurrentHashMap<>();
        counterMap.forEach((method, count) -> stats.put(method, count.get()));
        return Collections.unmodifiableMap(stats);
    }
}