package am.itspace.hotelManagement.enums;

public enum Rate {
  FIVE(5),
  FOUR(4),
  THREE(3),
  TWO(2),
  ONE(1);

  private final int value;

  Rate(int value) {
    this.value = value;
  }
}
