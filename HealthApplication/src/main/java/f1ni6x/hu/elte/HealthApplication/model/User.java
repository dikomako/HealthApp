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
    private Integer userID;

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

    private boolean isWaterAmountOK = false;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    @JsonIgnore
    private List<CupType> myCups;
}
