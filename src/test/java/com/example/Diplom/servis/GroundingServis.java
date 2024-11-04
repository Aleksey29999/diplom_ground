 package com.example.Diplom.servis;

import com.example.Diplom.models.Grounding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroundingServisTest {
    private GroundingServis groundingServis;
    private Grounding grounding;

    @BeforeEach
    void setUp() {
        groundingServis = new GroundingServis();
        grounding = new Grounding();
    }

    @Test
    void testAddGrounding() {
        groundingServis.addGrounding(grounding);
        // Проверяем, что заземление добавлено
        assertNotNull(grounding);
    }

    @Test
    void testAddGrounding_NullGrounding() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            groundingServis.addGrounding(null);
        });
        assertEquals("Grounding must not be null", thrown.getMessage());
    }

    @Test
    void testCalculationСlimaticRegion1() {
        grounding.setClimaticRegion1(1.9);
        groundingServis.calculationСlimaticRegion1(grounding);
        assertEquals(5.75, grounding.getClimaticRegion2());

        grounding.setClimaticRegion1(1.7);
        groundingServis.calculationСlimaticRegion1(grounding);
        assertEquals(4.0, grounding.getClimaticRegion2());

        grounding.setClimaticRegion1(1.45);
        groundingServis.calculationСlimaticRegion1(grounding);
        assertEquals(2.25, grounding.getClimaticRegion2());

        grounding.setClimaticRegion1(1.3);
        groundingServis.calculationСlimaticRegion1(grounding);
        assertEquals(1.75, grounding.getClimaticRegion2());
    }

    @Test
    void testCalculationTotalElectricalResistanceGround() {
        grounding.setResistanceUpGround(1);
        grounding.setResistanceDownGround(2);
        grounding.setLengthVerticalGrounding(5);
        grounding.setDepthHorizontalGrounding(3);
        grounding.setWidthUpGrounding(1);

        groundingServis.calculationTotalElectricalResistanceGround(grounding);
        assertNotNull(grounding.getTotalElectricalResistanceGround());
    }

    @Test
    void testCalculationTotalElectricalResistanceGround_DivideByZero() {
        grounding.setResistanceUpGround(0);
        grounding.setResistanceDownGround(0);
        grounding.setLengthVerticalGrounding(0);
        grounding.setDepthHorizontalGrounding(0);
        grounding.setWidthUpGrounding(0);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            groundingServis.calculationTotalElectricalResistanceGround(grounding);
        });
        assertEquals("must not be /0", thrown.getMessage());
    }

    @Test
    void testCalculationResistanceSingleVerticalGrounding() {
        grounding.setLengthVerticalGrounding(10);
        grounding.setDepthHorizontalGrounding(5);
        grounding.setDiametrVerticalGrounding(2);

        groundingServis.calculationResistanceSingleVerticalGrounding(grounding);
        assertNotNull(grounding.getResistanceSingleVerticalGrounding());
    }

    @Test
    void testCalculationResistanceSingleVerticalGrounding_DivideByZero() {
        grounding.setLengthVerticalGrounding(0);
        grounding.setDepthHorizontalGrounding(0);
        grounding.setDiametrVerticalGrounding(0);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            groundingServis.calculationResistanceSingleVerticalGrounding(grounding);
        });
        assertEquals("must not be /0", thrown.getMessage());
    }

    @Test
    void testCalculationResistanceTotalVerticalGrounding() {
        grounding.setCountVerticalGrounding(2);
        grounding.setUtilizationFactor1(1);
        grounding.setResistanceSingleVerticalGrounding(10);

        groundingServis.calculationResistanceTotalVerticalGrounding(grounding);
        assertNotNull(grounding.getResistanceTotalVerticalGrounding());
    }

    @Test
    void testCalculationResistanceTotalVerticalGrounding_DivideByZero() {



        grounding.setCountVerticalGrounding(0);
        grounding.setUtilizationFactor1(0);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            groundingServis.calculationResistanceTotalVerticalGrounding(grounding);
        });
        assertEquals("must not be /0", thrown.getMessage());
    }

    @Test
    void testCalculationResistanceHorizontalGrounding() {
        grounding.setCountHorizontalGrounding(2);
        grounding.setWidthHorizontalGrounding(3);
        grounding.setDepthHorizontalGrounding(1);
        grounding.setClimaticRegion2(5);
        grounding.setTotalElectricalResistanceGround(10);

        groundingServis.calculationResistanceHorizontalGrounding(grounding);
        assertNotNull(grounding.getResistanceHorizontalGrounding());
    }

    @Test
    void testCalculationResistanceHorizontalGrounding_DivideByZero() {
        grounding.setCountHorizontalGrounding(0);
        grounding.setWidthHorizontalGrounding(0);
        grounding.setDepthHorizontalGrounding(0);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            groundingServis.calculationResistanceHorizontalGrounding(grounding);
        });
        assertEquals("must not be /0", thrown.getMessage());
    }
}


