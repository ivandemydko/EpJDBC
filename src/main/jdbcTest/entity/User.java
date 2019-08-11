package main.jdbcTest.entity;


public class User {

  private Long id;
  private String name;
  private String lastName;
  private Double balance;
  private Long userTypeId;

  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }


  public Long getUserTypeId() {
    return userTypeId;
  }

  public void setUserTypeId(Long userTypeId) {
    this.userTypeId = userTypeId;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", lastName='" + lastName + '\'' +
            ", balance=" + balance +
            ", userTypeId=" + userTypeId +
            '}';
  }
}
