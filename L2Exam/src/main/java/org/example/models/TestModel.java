package org.example.models;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TestModel implements Comparable<TestModel> {
    private String name;
    private String method;
    private String status;
    private String startTime;
    private String endTime;
    private String duration;
    private String projectName;
    private String sid;
    private String env;

    @Override
    public int compareTo(TestModel o) {
        return o.getStartTime().compareTo(this.startTime);
    }

    public void setStatus(String status) {
        this.status = status.toLowerCase();
    }
}
