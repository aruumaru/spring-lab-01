package kz.iitu.spring_lab_01;

import kz.iitu.spring_lab_01.aspect.CallCounterAspect;
import kz.iitu.spring_lab_01.service.CatalogService;
import org.springframework.aop.support.AopUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class Lab4Controller {

    private final CatalogService catalogService;
    private final CallCounterAspect callCounterAspect; // Внедряем аспект

    public Lab4Controller(CatalogService catalogService, CallCounterAspect callCounterAspect) {
        this.catalogService = catalogService;
        this.callCounterAspect = callCounterAspect;
    }

    @GetMapping("/api/lab4/item/{id}")
    public String item(@PathVariable long id) {
        return catalogService.findById(id);
    }

    @GetMapping("/api/lab4/items")
    public List<String> items(@RequestParam(defaultValue = "5") int limit) {
        return catalogService.findAll(limit);
    }

    @DeleteMapping("/api/lab4/item/{id}")
    public String remove(@PathVariable long id) {
        return catalogService.remove(id);
    }

    @GetMapping("/api/lab4/proxy")
    public Map<String, String> proxyInfo() {
        return Map.of(
                "className",  catalogService.getClass().getName(),
                "superClass", catalogService.getClass().getSuperclass().getSimpleName(),
                "isAopProxy", String.valueOf(AopUtils.isAopProxy(catalogService)),
                "isCglib",    String.valueOf(AopUtils.isCglibProxy(catalogService))
        );
    }

    @GetMapping("/api/lab4/remove-twice/{id}")
    public String removeTwice(@PathVariable long id) {
        return catalogService.removeTwice(id);
    }

    // === Вариант 1: Эндпоинт для просмотра статистики вызовов ===
    @GetMapping("/api/lab4/stats")
    public Map<String, Long> getStats() {
        return callCounterAspect.getStats();
    }
}