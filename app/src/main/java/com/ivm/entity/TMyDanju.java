package com.ivm.entity;

import java.math.BigDecimal;

/**
 * Created by lk on 2017/8/12.
 */

public class TMyDanju extends TDanju {
      public String zdleixing(){
          int a=this.getDjZhangwuLeixing();
          if(a==1||a==2||a==3||a==6){
              return "充值";
          }
          if(a==7){
              return "消费";
          }
          return "";
      }

    public String zdjine(){
        int a=this.getDjZhangwuLeixing();

        BigDecimal cost;
        BigDecimal ivb=this.getKhIVBChange();
        BigDecimal lsb=this.getKhLSBChange();
        BigDecimal shb=this.getKhSHBChange();
        if(a==1){
            return ivb.toString();
        }
        if(a==2){
            return shb.toString();
        }
        if(a==3){
            return shb.toString();
        }
        if(a==6){
            return shb.toString();
        }
        if(a==7){
            cost=shb.add(ivb.add(lsb));
//            cost.toString();
            return cost.toString();
        }
        return "";
    }
}
