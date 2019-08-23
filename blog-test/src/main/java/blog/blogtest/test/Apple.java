package blog.blogtest.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;


public class Apple {
    private Float price;
    private String color;

    public Apple(Float price, String color) {
        this.price = price;
        this.color = color;
    }

    /**
     * 制造苹果,这里生成 9 只
     *
     * @return
     */
    public static List<Apple> generateApples() {
        List<Float> prices = Arrays.asList(2.5f, 1.3f, 3.2f);
        List<String> colors = Arrays.asList("red", "green", "black");
        return prices.stream().flatMap(
                p -> colors.stream().map(c -> new Apple(p, c))).collect(toList());
    }

}
