package net.p2pexchangehub.client.web.components;

import com.vaadin.addon.contextmenu.GridContextMenu;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.Grid;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.data.mongodb.core.MongoOperations;
import org.tylproject.vaadin.addon.MongoContainer;

import net.p2pexchangehub.client.web.ThemeStyles;

@ViewScoped
public abstract class MongoGrid<T> extends Grid {

    public static final String THEME_STYLE_WARNING = ThemeStyles.GRID_CELL_STYLE_WARNING;

    public static final String THEME_STYLE_GOOD = ThemeStyles.GRID_CELL_STYLE_GOOD;

    public static final String THEME_STYLE_GOOD_HIGHLIGHT = ThemeStyles.GRID_CELL_STYLE_GOOD_HIGHLIGHT;
    
    public static final String THEME_STYLE_ERROR = ThemeStyles.GRID_CELL_STYLE_ERROR;
    
    private final Class<T> clazz;
    
    @Inject
    private MongoOperations mongoOperations;

    private MongoContainer<T> dataContainer;

    private GridContextMenu contextMenu;

    public MongoGrid(Class<T> clazz) {
        super();
        this.clazz = clazz;
        contextMenu = new GridContextMenu(this);
        addStyleName(ThemeStyles.GRID_COLORED);
    }
    
    protected abstract Class<T> getItemClass();
    
    @PostConstruct
    private void init() {
        dataContainer = MongoContainer.Builder.forEntity(clazz, mongoOperations).buildBuffered();
        this.setContainerDataSource(dataContainer);        
    }
    
    public GridContextMenu getContextMenu() {
        return contextMenu;
    }

    public void refresh() {
        //mongocontainer is lazy, we need to trick grid to refresh cached data 
        super.setSortOrder(new ArrayList<>(super.getSortOrder()));
    }
    
    @SuppressWarnings("unchecked")
    public MongoContainer<T> getMongoContainerDataSource() {
        return (MongoContainer<T>) super.getContainerDataSource();
    }    
    
    public T getSelectedEntity() {
        Object itemId = getSelectedRow();
        return itemId==null ?  null : getMongoContainerDataSource().getItem(itemId).getBean();
    }

    public T getEntity(Object itemId) {
        return itemId==null ?  null : getMongoContainerDataSource().getItem(itemId).getBean();
    }
    
}
