package com.craftgate.restaurant.converter;

import com.craftgate.restaurant.dto.MenuItemDto;
import com.craftgate.restaurant.entity.MenuItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuItemConverter {

    public List<MenuItem> to(List<MenuItemDto> menuItemDtos) {
        List<MenuItem> menuItems = new ArrayList<>();
        for (MenuItemDto menuItemDto : menuItemDtos) {
            menuItems.add(to(menuItemDto));
        }
        return menuItems;
    }

    private MenuItem to(MenuItemDto menuItemDto) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemDto.getName());
        menuItem.setDefaultPrice(menuItemDto.getDefaultPrice());
        return menuItem;
    }
}
