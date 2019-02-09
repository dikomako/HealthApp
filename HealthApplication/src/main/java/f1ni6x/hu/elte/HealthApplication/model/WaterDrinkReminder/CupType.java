package f1ni6x.hu.elte.HealthApplication.model.WaterDrinkReminder;

import f1ni6x.hu.elte.HealthApplication.model.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "TYPE_OF_CUPS")
public class CupType {

    @Id
    @Column(name = "CUP_ID")
    private Integer cupID;

    @Column(name = "CUP_NAME")
    private String cupName;

    @Column(name = "CUP_CAPACITY")
    private long cupCapacity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User owner;
}
