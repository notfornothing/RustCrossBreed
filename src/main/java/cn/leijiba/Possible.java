package cn.leijiba;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true,fluent = true)
public class Possible {
    List<List<Gene>> origin;
    List<String> result;
    Integer rate;
}
