package altimetrik.playground.dec.colleges.domains;

import lombok.Data;
import java.util.List;

@Data
public class ResponseData {
    private MetaData metaData;
    private List<Result> resultSet;
}
