package f1ni6x.hu.elte.HealthApplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import f1ni6x.hu.elte.HealthApplication.model.WaterDrinkReminder.CupType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "USER_ID")
    private long userID;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @Column(name = "USER_CURRENT_WEIGHT")
    private long currentWeight;

    @Column(name = "USER_GOAL_WEIGHT")
    private long goalWeight;

    @Column(name = "USER_HEIGHT")
    private long height;

    @Column(name = "USER_AGE")
    private Integer age;

    @Column(name = "USER_PASSWORD")
    private String password;

    // ebben a változóban fogom eltárolni a kiszámolt napi, szükséges vízmennyiséget, amihez majd a teljesítést fogom mérni
    @Column(name = "DAILY_WATER_AMOUNT")
    private long dailyWaterAmount;

    // ebben a változóban fogom letárolni a felhasználó által eddig elfogyasztott vízmennyiséget
    @Column(name = "CURRENT_WATER_AMOUNT")
    private long currentWaterAmount;

    @Column(name = "USER_ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        ROLE_GUEST, ROLE_USER, ROLE_ADMIN
    }

    @Column(name = "USER_GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public enum Gender {
        MALE, FEMALE
    }

    @Column(name = "CALCULATED_BODY_MASS_INDEX_VALUE")
    private double calculatedBodyMassIndexValue;

    @Column(name = "CALCULATED_BODY_MASS_INDEX_CATEGORY")
    private String personalBodyMassIndexCategory;

    @Column(name = "CALCULATED_DAILY_CALORIE_INTAKE")
    private long calculatedDailyCalorieIntake;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    @JsonIgnore
    private List<CupType> myCups;

    public void calculateBMIvalue() {

        this.calculatedBodyMassIndexValue =  ( ((double)this.currentWeight) / (this.height * this.height ) ) * 10000;

        if( this.calculatedBodyMassIndexValue < 15.99 ) {
            this.personalBodyMassIndexCategory = "Severely underweight";
        } else if( this.calculatedBodyMassIndexValue >= 16.00 && this.calculatedBodyMassIndexValue < 16.99 ) {
            this.personalBodyMassIndexCategory = "Mild underweight";
        } else if( this.calculatedBodyMassIndexValue >= 17.00 && this.calculatedBodyMassIndexValue < 18.49 ) {
            this.personalBodyMassIndexCategory = "Underweight";
        } else if( this.calculatedBodyMassIndexValue >= 18.50 && this.calculatedBodyMassIndexValue < 24.99 ) {
            this.personalBodyMassIndexCategory = "Normal, healthy weight";
        } else if( this.calculatedBodyMassIndexValue >= 25.00 && this.calculatedBodyMassIndexValue < 29.99 ) {
            this.personalBodyMassIndexCategory = "Overweight";
        } else if( this.calculatedBodyMassIndexValue >= 30.00 && this.calculatedBodyMassIndexValue < 34.99 ) {
            this.personalBodyMassIndexCategory = "Moderately obese";
        } else if( this.calculatedBodyMassIndexValue >= 35.00 && this.calculatedBodyMassIndexValue < 39.99 ) {
            this.personalBodyMassIndexCategory = "Severely obese";
        } else if( this.calculatedBodyMassIndexValue >= 40.00 ) {
            this.personalBodyMassIndexCategory = "Very severely obese";
        }

        calculateCaloricIntake();
    }

    public void calculateCaloricIntake() {

        if( this.gender == Gender.MALE ) {
            this.calculatedDailyCalorieIntake = (long) (10 * this.currentWeight + 6.25 * this.height - 5 * this.age + 5);

        } else if( this.gender == Gender.FEMALE ) {
            this.calculatedDailyCalorieIntake = (long) (10 * this.currentWeight + 6.25 * this.height - 5 * this.age - 161);
        }

        System.out.println("Daily caloric intake: " + this.calculatedDailyCalorieIntake);
    }
}
