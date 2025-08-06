package kh.com.csx.dto;

import kh.com.csx.entity.Category;
import kh.com.csx.entity.Menu;
import kh.com.csx.entity.MerchantTheme;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WebsiteDTO {
    private MerchantDTO merchant;
    private List<Menu> menus;
    private List<Category> categories;
    private MerchantTheme merchantTheme;
}
