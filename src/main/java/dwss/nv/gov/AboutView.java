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

public class AboutView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "About";

    public AboutView() {
        CustomLayout aboutContent = new CustomLayout("aboutview");
        aboutContent.setStyleName("about-content");

        // you can add Vaadin components in predefined slots in the custom
        // layout
        aboutContent.addComponent(
                new Label(FontAwesome.INFO_CIRCLE.getHtml()
                        + " This application is using Vaadin "
                        + Version.getFullVersion(), ContentMode.HTML), "info");

        setSizeFull();
        setStyleName("about-view");
        addComponent(aboutContent);
        setComponentAlignment(aboutContent, Alignment.MIDDLE_CENTER);
        
        
        
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        addComponent(layout);
        //setContent(layout);

        SimpleFileDownloader downloader = new SimpleFileDownloader();
        addExtension(downloader);
        
        Button button = new Button("Download");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                final StreamResource resource = new StreamResource(() -> {
                    return new ByteArrayInputStream("This is test clicked on button".getBytes());
                }, "testButton.txt");
                
                downloader.setFileDownloadResource(resource);
                downloader.download();
            }
        });
        layout.addComponent(button);        
        
        // BEGIN-EXAMPLE: component.upload.basic
        // Show uploaded file in this placeholder
        final Image image = new Image("Uploaded Image");
        image.setVisible(false);

        // Implement both receiver that saves upload in a file and
        // listener for successful upload  add inner class
        
        class ImageReceiver implements Receiver, SucceededListener {
            private static final long serialVersionUID = -1276759102490466761L;

            public File file;
            
            public OutputStream receiveUpload(String filename,
                                              String mimeType) {
                // Create upload stream
                FileOutputStream fos = null; // Stream to write to
                try {
                    // Open the file for writing.
                    file = new File("/tmp/uploads/" + filename);
                    fos = new FileOutputStream(file);
                } catch (final java.io.FileNotFoundException e) {
                    new Notification("Could not open file<br/>",
                                     e.getMessage(),
                                     Notification.Type.ERROR_MESSAGE)
                        .show(Page.getCurrent());
                    return null;
                }
                return fos; // Return the output stream to write to
            }

            public void uploadSucceeded(SucceededEvent event) {
                // Show the uploaded file in the image viewer
                image.setVisible(true);
                image.setSource(new FileResource(file));
            }
        };
        
        ImageReceiver receiver = new ImageReceiver(); 

        // Create the upload with a caption and set receiver later
        final Upload upload = new Upload("Upload it here", receiver);
        upload.setButtonCaption("Start Upload");
        upload.addSucceededListener(receiver);
        
        // Prevent too big downloads
        final long UPLOAD_LIMIT = 1000000l;
        upload.addStartedListener(new StartedListener() {
            private static final long serialVersionUID = 4728847902678459488L;

            @Override
            public void uploadStarted(StartedEvent event) {
                if (event.getContentLength() > UPLOAD_LIMIT) {
                    Notification.show("Too big file",
                        Notification.Type.ERROR_MESSAGE);
                    upload.interruptUpload();
                }
            }
        });
        
        // Check the size also during progress 
        upload.addProgressListener(new ProgressListener() {
            private static final long serialVersionUID = 8587352676703174995L;

            @Override
            public void updateProgress(long readBytes, long contentLength) {
                if (readBytes > UPLOAD_LIMIT) {
                    Notification.show("Too big file",
                        Notification.Type.ERROR_MESSAGE);
                    upload.interruptUpload();
                }
            } 
        });

        // Put the components in a panel
        Panel panel = new Panel("Cool Image Storage");
        Layout panelContent = new VerticalLayout();
        panelContent.addComponents(upload, image);
        panel.setContent(panelContent);
        // END-EXAMPLE: component.upload.basic

        // Create uploads directory
        File uploads = new File("/tmp/uploads");
        if (!uploads.exists() && !uploads.mkdir())
            layout.addComponent(new Label("ERROR: Could not create upload dir"));

        ((VerticalLayout) panel.getContent()).setSpacing(true);
        panel.setWidth("-1");
        layout.addComponent(panel);
        
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

    
    
}








