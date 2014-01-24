package studentflashcard;

/**
 *
 * @author Neel
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ProjectListPanel extends JPanel{
    //the panel on the left side of the GUI
    
    private Controller controller;
    private GUI gui;
    private CopyFlashCard card;
    
    private ArrayList<Project> projects; //the projects that the user has
    
    private JPanel projectPanelHolder;
    private ArrayList<ProjectPanel> projectPanels = new ArrayList<ProjectPanel>();
    private ProjectPanel activeProjectPanel = null;
    
    public static final int MY_WIDTH = 200;
    public static final int MY_HEIGHT = 500;
    
    public ProjectListPanel(ArrayList<Project> projects,Controller controller, GUI gui){
        this.projects = projects;
        this.setPreferredSize(new Dimension(MY_WIDTH,MY_HEIGHT));
        this.controller = controller;
        this.gui = gui;
    }
    
    public void createPanel(){
        setLayout(new BorderLayout());
        
        //project adding buttons go in the bottom
        JPanel bottomHolder = new JPanel(new GridLayout(0,1)); //however many rows as are needed
            //plain old project creation
            JButton addProject = new JButton("Create a project", ImageManager.createImageIcon("plus.png"));
            addProject.addActionListener(new addProjectListener());
            bottomHolder.add(addProject);
            //import a project
            JButton importProject = new JButton("Import a project", ImageManager.createImageIcon("import.png"));
            importProject.addActionListener(new ImportProjectListener());
            bottomHolder.add(importProject);
        add(BorderLayout.SOUTH, bottomHolder);
        
        //project panel holder
        this.projectPanelHolder = new JPanel();
        GridLayout layout = new GridLayout(0, 1);
        projectPanelHolder.setLayout(layout);
        projectPanelHolder.setBackground(ColorManager.translucent(Color.white, 160));
        //build project panels
        for(Project project : projects){
            addProject(project);
        }
        
        //put it in a scroller
        JScrollPane scrollPane = new JScrollPane(projectPanelHolder);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        //scrollPane.setBorder(null);
        add(BorderLayout.CENTER, scrollPane);
        
        //put space-eating glue in the center so neither the scroller or the buttons gets too much space
        //if there are few projects, scroller gets too huge and project panels get too spread out
        //add(BorderLayout.CENTER, Box.createVerticalGlue());
        //put them in the holder
        //refresh();
       
    }
     
    public void refresh(){
        //re-add project panels
        this.removeAll();
        createPanel();
        validate();
        repaint();
    }
    
    public void addProject(Project project){
        //add a new project panel
        ProjectPanel panel = new ProjectPanel(project, this);
        projectPanels.add(panel);
        //add to view
        projectPanelHolder.add(panel);
        validate();
        repaint();
    }
    
    /** If the given input has the same name as a project, the user is asked if they want to overwrite.
     * 
     * @param input the requested project name
     * @param whatToSay what to tell the user about the overwrite
     * @return true if the user is OK with overwrite or there's no overwrite, false if the user doesn't want to overwrite
     */
    private boolean confirmProjectOverwrite(String input,String whatToSay){
    //check if the name conflicts with any existing projects' names
        for(final Project project : controller.getAllProjects()){
            if(input.equals(project.getName())){
                //there's already a project with the same name
                //ask for confirmation "do you want to delete the old one?"
                String ask = whatToSay;   

                if(InputManager.confirm(ask, gui.getFrame()) == false){
                    //the user decided not to overwrite the old note
                    return false;
                }  
                else{
                    //the user decided to delete that project
                    controller.removeProject(project);           
                    //now refresh the list
                    //listModel.remove(listModel.indexOf(project)); 
                    return true;//no point looping through any more since we've found a match
                }
            }
        }
        //if you reached this point, no names conflict
        return true;
    }
    
    
    private class addProjectListener implements ActionListener{
        @Override
		public void actionPerformed(ActionEvent e){
            //when you click on the add project button
            //create a pop-up box to show something in
            //requestInput();
            String input = InputManager.getUserInput("Name of project:","",true,gui.getFrame());
            if(input == null)
                return; //that means they canceled
            
            //is there overwrite? check for it
            if(confirmProjectOverwrite(input,
                "<html><center>A project named <b>" + input + "</b> already exists.<br>Do you want to delete the old project and replace it with an empty one?")){
                //now make a project with that
                controller.addProject(input,true);  //the controller takes care of the view           
                //controller will call addProject(project)
                
                //gain points
                controller.gainPoints(PointEnums.Activity.CREATE_PROJECT);
            }
        }
    }
    
    private class ImportProjectListener implements ActionListener{
        @Override
		public void actionPerformed(ActionEvent e){
            //IE takes care of everything for us
            ImportExport.Import(controller, gui);
        }
    }
    
    public void renameProject(Project selectedProject){
            //get new name of project
            String input = InputManager.getUserInput("New name of " + selectedProject.getName() + ":",selectedProject.getName(),true,gui.getFrame());
            if(input == null)
                return; //that means they canceled
            
            if(input.equals(selectedProject.getName()) || 
                confirmProjectOverwrite(input,
                    "<html><center>A project named <b>" + input + "</b> already exists.<br>Do you want to delete the old project and replace it with " + selectedProject.getName() + "?")){
                //if the name wasn't changed, don't bother checking
                //change project name
                controller.renameProject(selectedProject, input);           

                //setting this project as active refreshes everything for us
                controller.setActiveProject(selectedProject, true);
               
                //update the look of the panel of the chosen project
                for(ProjectPanel panel : projectPanels){
                    if(panel.projectEquals(selectedProject)){
                        panel.refresh(); //updates text
                    }
                }
                
                validate();
                refresh();
            }        
    }
    
    public void deleteProject(Project project){
        //when you click the remove project button
        boolean didUserConfirm = InputManager.confirm("<html><center>Are you sure you want to delete <b>" + project.toString() + "</b>?",
                gui.getFrame());
            //ask for confirmation
        if(didUserConfirm){
            //the user said yes to deleting the project
            //a project was selected to be removed
            controller.removeProject(project);
            //remove that panel
            for(ProjectPanel panel : projectPanels){
                if(panel.projectEquals(project)){
                    //remove this
                    projectPanelHolder.remove(panel);
                    validate();
                    refresh();
                    return;
                }
            }
        }
    }

    public void activateProject(Project project){
        //make this project active
        controller.setActiveProject(project,true);
    }
    
    public void exportProject(Project project){
        ImportExport.export(project, controller, gui);
    }
    
    public void emailProject(Project project){
        ImportExport.email(project, controller, gui);
    }
    
    public void copyProject(Project project){
        ImportExport.copyProject(project, controller, gui);
    }

    
}
