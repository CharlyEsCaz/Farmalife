package Modelos;

import Modelos.util.JsfUtil;
import Modelos.util.PaginationHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.io.Serializable;
import java.util.Date;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;


@Named("productosController")
@SessionScoped
public class ProductosController implements Serializable {

    private Productos current;
    private DataModel items = null;
    
    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
   
    @EJB
    private Modelos.ProductosFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String today;

    public ProductosController() {
    }

    public Productos getSelected() {
        if (current == null) {
            current = new Productos();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ProductosFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }
   

    public String prepareList() {
        recreateModel();
        return "List";
    }
    
    public String Today() {
        java.util.Date fecha = new Date();
        this.today = fecha.getMonth()+"/"+fecha.getDay()+"/"+fecha.getYear();
        return today;
    }
    

    public String prepareView() {
        current = (Productos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }
    
    public String prepareViewCatalog() {
        current = (Productos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Productos();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        
        if(file.getContentType().equals("image/jpeg") || file.getContentType().equals("application/octet-stream")){
            try{
                current.setImagen(storageImage((pagination.getItemsCount()) + 1));
                getFacade().create(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProductosCreated"));
                return prepareCreate();
            }catch(Exception e){
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                return null;
            }
        }
        JsfUtil.addErrorMessage( ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        return null;
    }
    
    public String storageImage(int id){
        String path= "/resources/img/"+id+".jpg";
        File file2 = new File("C:/Users/CharlyEzCas/Documents/NetBeansProjects/Farmalife/web"+path);
        try (OutputStream outputStream = new FileOutputStream(file2)) {
                IOUtils.copy(file.getInputstream(), outputStream);
                return path;
            } catch (FileNotFoundException e) {
                // handle exception here
                JsfUtil.addErrorMessage(e, "No lo encontre xD");
            } catch (IOException e) {
                // handle exception here
                JsfUtil.addErrorMessage(e,"Error x_x");

            }
        return "";
    }

    public String prepareEdit() {
        current = (Productos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        if(file.getContentType().equals("image/jpeg") || file.getContentType().equals("application/octet-stream")){
            try{
                current.setImagen(storageImage(current.getIdproducto()));
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProductosCreated"));
                return "View";
            }catch(Exception e){
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                return null;
            }
        }
        JsfUtil.addErrorMessage( ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        return null;
  
    }

    public String destroy() {
        current = (Productos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProductosDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Productos getProductos(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Productos.class)
    public static class ProductosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProductosController controller = (ProductosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "productosController");
            return controller.getProductos(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Productos) {
                Productos o = (Productos) object;
                return getStringKey(o.getIdproducto());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Productos.class.getName());
            }
        }

    }

}
