package dwss.nv.gov;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.vaadin.simplefiledownloader.SimpleFileDownloader;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.Version;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

public class AdminView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "Admin";

    public AdminView() {
        CustomLayout adminContent = new CustomLayout("adminview");
        adminContent.setStyleName("about-content");

        // you can add Vaadin components in predefined slots in the custom
        // layout
        adminContent.addComponent(
                new Label(FontAwesome.GEARS.getHtml()
                        + " Admin View not yet implmented "
                        + Version.getFullVersion(), ContentMode.HTML), "settings");

        setSizeFull();
        setStyleName("about-view");
        addComponent(adminContent);
        setComponentAlignment(adminContent, Alignment.MIDDLE_CENTER);
        
        
        
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        addComponent(layout);

       
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

    
    
}








