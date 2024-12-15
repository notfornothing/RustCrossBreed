package cn.leijiba;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class Col {
    //growthSpeed
    Integer g;
    //Yield
    Integer y;
    //Hardiness
    Integer h;
    //Nothing
    Integer x;
    //Water
    Integer w;

    public Col() {
        g=0;
        y=0;
        h=0;
        x=0;
        w=0;
    }
}
