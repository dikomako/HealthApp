export class User {
    userID: number;
    userName: string;
    emailAddress: string;
    currentWeight: number;
    goalWeight: number;
    height: number;
    age: number;
    password: string;
    dailyWaterAmount: number;
    currentWaterAmount: number;
    role: string;
    gender: string;
    token: string;

    // constructor
    constructor(
        userID: number,
        userName: string,
        emailAddress: string,
        currentWeight: number,
        goalWeight: number,
        height: number,
        age: number,
        password: string,
        dailyWaterAmount: number,
        currentWaterAmount: number,
        role: string,
        gender: string) { 

            this.userID = userID;
            this.userName = userName;
            this.emailAddress = emailAddress;
            this.currentWeight = currentWeight;
            this.goalWeight = goalWeight;
            this.height = height;
            this.age = age;
            this.password = password;
            this.dailyWaterAmount = dailyWaterAmount;
            this.currentWaterAmount = currentWaterAmount;
            this.role = role;
            this.gender = gender;
     }
}