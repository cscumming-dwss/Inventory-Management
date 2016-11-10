package example.test;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Viewport;
import com.vaadin.annotations.Widgetset;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import example.test.authentication.AccessControl;
import example.test.authentication.BasicAccessControl;
import example.test.authentication.LoginScreen;
import example.test.authentication.LoginScreen.LoginListener;
import example.test.backend.data.ProductRepository;

/**
 * Main UI class of the application that shows either the login screen or the
 * main view of the application depending on whether a user is signed in.
 *
 * The @Viewport annotation configures the viewport meta tags appropriately on
 * mobile devices. Instead of device based scaling (default), using responsive
 * layouts.
 */


/**
 * Created by ccummings on 10/16/16.
 */
@SpringUI

@CDIUI("")
@Viewport("user-scalable=no,initial-scale=1.0")
//@Theme("valo")
@Theme("inventoryTheme")
public class VaadinUI extends UI {

	private static final long serialVersionUID = 1L;
	
	private AccessControl accessControl = new BasicAccessControl();
	
    @Autowired
    ProductRepository pRepository;
    //private MTable<Customer> g;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("Inventory");
        if (!accessControl.isUserSignedIn()) {
            setContent(new LoginScreen(accessControl, new LoginListener() {
                @Override
                public void loginSuccessful() {
                    showMainView();
                }
            }));
        } else {
            showMainView();
        }
    }

    protected void showMainView() {
        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(new MainScreen(VaadinUI.this));
        getNavigator().navigateTo(getNavigator().getState());
    }

    public static VaadinUI get() {
        return (VaadinUI) UI.getCurrent();
    }

    public ProductRepository getProductRepository(){
    	return pRepository;
    }
    
    public AccessControl getAccessControl() {
        return accessControl;
    }

}