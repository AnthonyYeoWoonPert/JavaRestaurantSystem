/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;

/**
 *
 * @author User
 */
public class CalculateTax {
    public static class ServiceTax extends Thread{
        private double withoutTax;
        private double serviceTax;
        
        public ServiceTax(){
        }

        public void setWithoutTax(double withoutTax) {
            this.withoutTax = withoutTax;
        }

        public double getServiceTax() {
            return serviceTax;
        }

        public void run(){
            serviceTax = 6.0 * withoutTax / 100.0;
        }
        
        
    }
    
    public static class ServiceCharge extends Thread{
        private double withoutTax;
        private double serviceCharge;
        
        public ServiceCharge(){
        }

        public void setWithoutTax(double withoutTax) {
            this.withoutTax = withoutTax;
        }

        public double getServiceCharge() {
            return serviceCharge;
        }

        public void run(){
            serviceCharge = 10.0 * withoutTax / 100.0;
        }
        
        
    }
}
