package com.example.Diplom.servis;
import com.example.Diplom.models.Grounding;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class GroundingServis {
    private final Grounding grounding = new Grounding();
    List<Grounding> groundings = new ArrayList<>();
    public void addGrounding(Grounding grounding) {
        if (grounding == null) {
            throw new IllegalArgumentException("Grounding must not be null");
        }
        groundings.clear();
        groundings.add(grounding);
        System.out.println(groundings.toString());
    }
    public void calculationСlimaticRegion1(Grounding grounding) { // выбор климатических коэффициентов
        if (grounding.getClimaticRegion1()==1.9) {
            grounding.setClimaticRegion2(5.75);
        }
        else
        if (grounding.getClimaticRegion1()==1.7) {
            grounding.setClimaticRegion2(4.0);
        }
        else
        if (grounding.getClimaticRegion1()==1.45) {
            grounding.setClimaticRegion2(2.25);
        }
        else
        if (grounding.getClimaticRegion1()==1.3) {
            grounding.setClimaticRegion2(1.75);
        }
    }
    public void calculationTotalElectricalResistanceGround(Grounding grounding) { // расчет удельного сопротивления грунта
        if (((grounding.getResistanceUpGround()*(grounding.getLengthVerticalGrounding()+grounding.getDepthHorizontalGrounding()-grounding.getWidthUpGrounding()))+
                (grounding.getResistanceDownGround()*(grounding.getWidthUpGrounding()-grounding.getDepthHorizontalGrounding()))) == 0 )  {
            throw new RuntimeException("must not be /0");
        }

       grounding.setTotalElectricalResistanceGround((grounding.getResistanceUpGround()*grounding.getResistanceDownGround()*grounding.getLengthVerticalGrounding())/
       ((grounding.getResistanceUpGround()*(grounding.getLengthVerticalGrounding()+grounding.getDepthHorizontalGrounding()-grounding.getWidthUpGrounding()))+
       (grounding.getResistanceDownGround()*(grounding.getWidthUpGrounding()-grounding.getDepthHorizontalGrounding()))));
           }

    public void calculationResistanceSingleVerticalGrounding(Grounding grounding) { // расчет сопротивления одиночного вертикального заземлителя
        if ((4*((0.5*grounding.getLengthVerticalGrounding())+grounding.getDepthHorizontalGrounding())-grounding.getLengthVerticalGrounding())== 0
                ||(grounding.getDiametrVerticalGrounding()==0 )) {
            throw new RuntimeException("must not be /0");
        }
        grounding.setResistanceSingleVerticalGrounding((Math.log10(2*grounding.getLengthVerticalGrounding()/grounding.getDiametrVerticalGrounding())+
        0.5*(Math.log10((4*((0.5*grounding.getLengthVerticalGrounding())+grounding.getDepthHorizontalGrounding())+grounding.getLengthVerticalGrounding())/
        (4*((0.5*grounding.getLengthVerticalGrounding())+grounding.getDepthHorizontalGrounding())-grounding.getLengthVerticalGrounding())))));
    }
    public void calculationResistanceTotalVerticalGrounding(Grounding grounding) { //общее сопротивление вертикальных заземлителей
        if ((grounding.getCountVerticalGrounding()*grounding.getUtilizationFactor1()) == 0 ) {
            throw new RuntimeException("must not be /0");
        }
        grounding.setResistanceTotalVerticalGrounding(grounding.getResistanceSingleVerticalGrounding()/
                (grounding.getCountVerticalGrounding()*grounding.getUtilizationFactor1()));
    }
    public void calculationResistanceHorizontalGrounding(Grounding grounding) { // сопротивление горизонтального заземлителя
        if ((grounding.getCountHorizontalGrounding() ==0) || (grounding.getWidthHorizontalGrounding()*grounding.getDepthHorizontalGrounding())==0 ) {
            throw new RuntimeException("must not be /0");
        }
       grounding.setResistanceHorizontalGrounding((grounding.getClimaticRegion2()*0.366*grounding.getTotalElectricalResistanceGround()/grounding.getCountHorizontalGrounding())*
       (Math.log10(2*grounding.getCountHorizontalGrounding()*grounding.getCountHorizontalGrounding()/(grounding.getWidthHorizontalGrounding()*grounding.getDepthHorizontalGrounding()))));
    }
    public void calculationTotalResistanceHorizontalGrounding(Grounding grounding) { // общее сопротивление горизонтального заземлителя с коэффициентами
        if (grounding.getUtilizationFactor2()==0 ) {
            throw new RuntimeException("must not be /0");
        }
        grounding.setResistanceTotalHorizontalGrounding(grounding.getResistanceHorizontalGrounding()/grounding.getUtilizationFactor2());
    }
    public void calculationtotalResistanceGrounding(Grounding grounding) {  // общее сопротивление контура заземления
        if ((grounding.getResistanceTotalHorizontalGrounding()+grounding.getResistanceHorizontalGrounding()) == 0 ) {
            throw new RuntimeException("must not be /0");
        }
        grounding.setTotalResistanceGrounding((grounding.getResistanceTotalHorizontalGrounding()*grounding.getResistanceHorizontalGrounding())/
        (grounding.getResistanceTotalHorizontalGrounding()+grounding.getResistanceHorizontalGrounding()));
    }
    public List<Grounding> getGroundings() {
        return new ArrayList<>(groundings);
    }

}
