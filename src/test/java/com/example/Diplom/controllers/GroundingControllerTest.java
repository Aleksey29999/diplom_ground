//Для тестирования контроллера GroundingController вы можете использовать библиотеку Spring Boot Test вместе с JUnit 5. Ниже приведен пример тестового класса, который проверяет основные функции контроллера, включая обработку GET и POST запросов.
//
//        Java

        package com.example.Diplom.controllers;

import com.example.Diplom.models.Grounding;
import com.example.Diplom.servis.GroundingServis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GroundingControllerTest {

    @InjectMocks
    private GroundingController groundingController;

    @Mock
    private GroundingServis groundingServis;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetGroundings() {
        // Arrange
        String expectedViewName = "groundings";

        // Act
        String viewName = groundingController.getGroundings(model);

        // Assert
        verify(groundingServis, times(1)).getGroundings();
        verify(model, times(1)).addAttribute("groundings", groundingServis.getGroundings());
        assertEquals(expectedViewName, viewName);
    }

    @Test
    void testAddGroundings() {
        // Arrange
        Grounding grounding = new Grounding();
        when(groundingServis.getGroundings()).thenReturn(List.of(grounding)); // Возвращаем список заземлений

        // Act
        String viewName = groundingController.addGroundings(grounding, model);

        // Assert
        verify(groundingServis, times(1)).calculationСlimaticRegion1(grounding);
        verify(groundingServis, times(1)).calculationTotalElectricalResistanceGround(grounding);
        verify(groundingServis, times(1)).calculationResistanceSingleVerticalGrounding(grounding);
        verify(groundingServis, times(1)).calculationResistanceTotalVerticalGrounding(grounding);
        verify(groundingServis, times(1)).calculationResistanceHorizontalGrounding(grounding);
        verify(groundingServis, times(1)).addGrounding(grounding);
        verify(model, times(1)).addAttribute("groundings", groundingServis.getGroundings());
        assertEquals("groundings", viewName);
    }
}
//
//### Объяснение кода:
//
//        1. Использование Mockito: Мы используем Mockito для создания моков для GroundingServis и Model. Это позволяет изолировать тестируемый код и избежать необходимости в реальных зависимостях.
//
//        2. Метод setUp(): Перед каждым тестом мы инициализируем моки с помощью MockitoAnnotations.openMocks(this).
//
//        3. Тесты:
//        - testGetGroundings(): Этот тест проверяет, что метод getGroundings() контроллера правильно вызывает метод getGroundings() из сервиса и добавляет полученные данные в модель.
//        - testAddGroundings(): Этот тест проверяет, что метод addGroundings() контроллера вызывает соответствующие методы сервиса для выполнения расчетов и добавления данных, а также передает данные обратно в модель.
//
//        ### Запуск тестов:
//        Чтобы запустить тесты, убедитесь, что у вас в проекте настроены зависимости для Spring Boot и Mockito. Обычно это делается через Maven или Gradle, добавляя соответствующие зависимости в файл pom.xml или build.gradle.
//
//        ### Примечания:
//        - Убедитесь, что в вашем коде контроллера завершены все необходимые зависимости и методы, чтобы избежать ошибок при тестировании.
//        - Настройте ваши модели/сервисы в соответствии с вашими требованиями, чтобы тесты работали корректно.

