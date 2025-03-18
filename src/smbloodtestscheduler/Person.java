/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smbloodtestscheduler;
class Person {
    private String name;
    private String urgency;
    private int age;
    private boolean fromHospitalWard;
    private String gpDetails;
    private String details;
    private boolean NoShow;

    public Person(String name, String urgency, int age, boolean fromHospitalWard, String gpDetails, boolean NoShow) {
        this.name = name;
        this.urgency = urgency;
        this.age = age;
        this.fromHospitalWard = fromHospitalWard;
        this.gpDetails = gpDetails;
        this.NoShow = NoShow;
    }
    public Person(String name, String urgency, int age, boolean fromHospitalWard, String gpDetails) {
        this.name = name;
        this.urgency = urgency;
        this.age = age;
        this.fromHospitalWard = fromHospitalWard;
        this.gpDetails = gpDetails;
        
    }

    public void setNoShow(boolean NoShow) {
        this.NoShow = NoShow;
    }

    public boolean isNoShow() {
        return NoShow;
    }

    
    public String getName() {
        return name;
    }

    public String getUrgency() {
        return urgency;
    }

    public int getAge() {
        return age;
    }

    public String getGpDetails() {
        return gpDetails;
    }

    public String getDetails() {
        return details;
    }
    

    public boolean isFromHospitalWard() {
        return fromHospitalWard;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Urgency: " + urgency + ", Age: " + age + ", From Hospital Ward: " + fromHospitalWard + ", GP: " + gpDetails + "No Show: " + NoShow;
    }
}




