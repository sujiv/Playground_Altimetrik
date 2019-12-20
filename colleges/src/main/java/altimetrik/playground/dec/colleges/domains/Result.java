package altimetrik.playground.dec.colleges.domains;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
//"results":[{"school.name":"Cumberland County College","2013.student.size":3814,"id":184205}]}
public class Result implements Comparable {
    Integer id;
    String schoolName;
    Integer year;
    Integer studentSize;

    public Result(Integer i, String name, Integer size, Integer year) {
        this.id = i;
        this.schoolName = name;
        this.studentSize = size;
        this.year = year;

    }

    public Result() {
        this.id = -1;
        this.schoolName = "!Not Provided";
        this.year = -1;
        this.studentSize = -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return id == result.id &&
                year == result.year &&
                studentSize == result.studentSize &&
                schoolName.equalsIgnoreCase(result.schoolName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, schoolName, year, studentSize);
    }

    @Override
    public int compareTo(Object o) {
        if(this == o) return 0;
        if(o == null) return 1;
        Result result =  (Result) o;
        return this.studentSize.compareTo(result.studentSize);
    }
}
