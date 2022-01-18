package numble.carrotmarket.product.domain;

public enum ProductState {
    SOLDING("판매중"),
    RESERVING("예약중"),
    COMPLETED("거래 완료");

    private final String state;

    ProductState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
