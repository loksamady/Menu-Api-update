package kh.com.csx.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class ValueLabelDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String value;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String label;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String labelKh;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String labelEn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean checked;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ValueLabelDTO> list;

    public void add(ValueLabelDTO dto) {
        list.add(dto);
    }

    public ValueLabelDTO(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public static class ValueLabelDTOBuilder {
        public ValueLabelDTOBuilder checked(String board) {
            if (value != null && value.equals(board)) {
                checked = true;
            } else {
                checked = null;
            }
            return this;
        }
    }
}
