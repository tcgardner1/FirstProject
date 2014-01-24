/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studentflashcard;

//import javax.swing.*;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/** For importing/exporting projects.
 *
 * @author Neel
 */
public class ImportExport extends Object{
    
     private ImportExport(){}
    
    /** Exports a project. Call this if you DON'T know which project you want to export - the user will be prompted to choose one.
     * 
     * @param controller the controller
     * @param gui the gui
     */
    public static void export(Controller controller, GUI gui){
        if(controller.getNumberOfProjects() == 0){
            //no projects, so no exportation
            return;
        }

        //ask for a project to export
        Project selectedProject = InputManager.getProject("Which project do you want to export?", controller.getAllProjects().toArray(new Project[]{}), gui.getFrame());
        if(selectedProject == null)
            return;

        export(selectedProject, controller, gui); 
    }
    
    /**
     * The real export method. Exports the given project by putting it in a zipped file at a location the user chooses.
     * @param project the projec to export
     * @param controller the controller object
     * @param gui the gui object
     */
    public static void export(final Project project, final Controller controller, final GUI gui){
        //export in background
        new Thread(new Runnable(){
            @Override
			public void run(){
                String name = project.getName();

                //ask where to export to
                java.io.File whereToExport = InputManager.requestFolder(gui.getFrame(),"Where do you want to save " + name + "?");
                /*while(true){
                    whereToExport = InputManager.requestFolder(gui.getFrame(),"Where do you want to save " + name + "?");
                    if(whereToExport != null && whereToExport.exists()){
                        //you're liable to overwrite something
                        boolean ok = InputManager.confirm(
                                "<html>The file <b>" + whereToExport.getAbsolutePath() + "</b> already exists. Are you sure you want to overwrite it?", 
                                gui.getFrame());
                        if(ok == false){
                            //user changed their mind; they want to save elsewhere
                            continue;
                        }
                    }                     
                    break;
                }*/


                if(whereToExport != null){
                    //we'll assume you're exporting active project
                    java.io.File projectFolder = project.getFolder();

                    //zip it
                    java.io.File zipped = Zipper.zip(projectFolder, whereToExport, gui);

                    if(zipped == null){
                        //the user canceled the zipping
                    }
                    else{
                        //show a success message
                        Utils.showDialog(
                                            gui.getFrame(), //parent
                                            "<html><center><b>" + name + "</b> has been exported to<br><i>" + zipped.getAbsolutePath() + "</i>.", //text
                                            name + " has been exported" //title
                                        );
                        //gain points
                        controller.gainPoints(PointEnums.Activity.EXPORT_PROJECT);
                    }
                }                
            }
        }).start();
    }
    
    /** Imports a project.
     * 
     * @param controller
     * @param gui 
     */
    public static void Import(Controller controller, GUI gui){
        //ask which file to import
        java.io.File fileToImport = InputManager.requestZipFile(gui.getFrame(),"Choose a project to import:");

        //String finalProjectName; //like projectName, except it stores the project's actual name

        if(fileToImport != null){
            //get name of project (it's the name of the .zip file without the .zip extension)
            String projectName = fileToImport.getName().substring(0,fileToImport.getName().length() - (Zipper.EXTENSION.length() + 1));
            //finalProjectName = projectName;
            projectName = Sanitizer.sanitize(projectName); //generally, people won't be able to put disallowed chars in zip file name, but sanitize anyway
            File projectFile = new File(SaveLoad.getProjectFolder() + "/" + projectName);

            //if a project of that name already exists, ask for a new name (or just remove project with old name)
            for(Project project : controller.getAllProjects()){
                if(project.getName().equals(projectName)){
                    //found it
                    /*//ask for a new name
                    String input = InputManager.getUserInput("<html><center>A project with the name <b>" + projectName + "</b> already exists.<br>Enter a new name for the project you have imported,<br>or hit cancel to overwrite the old project.", 
                            projectName, true, gui.getFrame());
                    if(input == null){
                        //they're OK with overwriting; keep the name but delete the old project
                        controller.removeProject(project);
                    }
                    else{
                        //they aren't OK with it; rename the file to be imported
                        File newFile = new File(projectFile.getParent() + "/" + input);
                        projectFile.renameTo(newFile);
                        //by doing this, the project file will have a different name so the imported project will have a different name
                    }
                    break;*/
                    controller.removeProject(project);
                    break;
                }
            }


            //unzip it to project folder
            Zipper.unzip(fileToImport, projectFile); 

            //add a project based on project name
            controller.createProjectFromExistingFile(projectName, projectFile);
            //controller.getActiveProject().setName(finalProjectName);
            //controller.refresh();      
            
            //gain points
            controller.gainPoints(PointEnums.Activity.IMPORT_PROJECT);
        }
    }

	public static void email(final Project project, final Controller controller, final GUI gui) {
        //export in background
        
                
              MailSender mailsend = new MailSender(controller,project);
                


               
    }

	/**
	 * @param project
	 * @param controller
	 * @param gui
	 */
	public static void copyProject(Project project, Controller controller,
			GUI gui){
        //ask which file to import
       // java.io.File fileToImport = InputManager.requestZipFile(gui.getFrame(),"Choose a project to import:");

        
        java.io.File projectFolder = project.getFolder();
        fileZipforAttachment(projectFolder);
		// jLabel5.setText(zip.getName());
		File fileToImport = new File(projectFolder.getAbsolutePath() + "/"
				+ project.getName() + "." + Zipper.EXTENSION);
        if(fileToImport != null){
            //get name of project (it's the name of the .zip file without the .zip extension)
            String projectName = fileToImport.getName().substring(0,fileToImport.getName().length() - (Zipper.EXTENSION.length() + 1));
            //finalProjectName = projectName;
            projectName = Sanitizer.sanitize(projectName); //generally, people won't be able to put disallowed chars in zip file name, but sanitize anyway
            projectName = projectName + "copy";
            File projectFile = new File(SaveLoad.getProjectFolder() + "/" + projectName);
            
            //if a project of that name already exists, ask for a new name (or just remove project with old name)
            for(Project project1 : controller.getAllProjects()){
                if(project1.getName().equals(projectName)){
                    //found it
                    /*//ask for a new name
                    String input = InputManager.getUserInput("<html><center>A project with the name <b>" + projectName + "</b> already exists.<br>Enter a new name for the project you have imported,<br>or hit cancel to overwrite the old project.", 
                            projectName, true, gui.getFrame());
                    if(input == null){
                        //they're OK with overwriting; keep the name but delete the old project
                        controller.removeProject(project);
                    }
                    else{
                        //they aren't OK with it; rename the file to be imported
                        File newFile = new File(projectFile.getParent() + "/" + input);
                        projectFile.renameTo(newFile);
                        //by doing this, the project file will have a different name so the imported project will have a different name
                    }
                    break;*/
                   // controller.removeProject(project1);
                    break;
                }
            }


            //unzip it to project folder
            Zipper.unzip(fileToImport, projectFile); 

            //add a project based on project name
            controller.createProjectFromExistingFile(projectName, projectFile);
            //controller.getActiveProject().setName(finalProjectName);
            //controller.refresh();      
            
            //gain points
            controller.gainPoints(PointEnums.Activity.IMPORT_PROJECT);
        }
    }
	
    
    /** Imports a project from a plain-text (CSV) file.
     * 
     * @param plainTextFile the file that contains the cards as plain text (question/answer)
     * @param projectName the preferred name of the project once it's imported
     * @return the project created from the import
     */
    /*public static Project textImport(File plainTextFile, String projectName){
        Project project = new Project(projectName);
        
        //get cards from file
        ArrayList<Card> cards = new ArrayList<Card>();
        
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(plainTextFile));
            String line = null;
            while((line = reader.readLine()) != null){
                String[] elements = line.split(Card.DELIMITER);
                //elements = question, answer
                try{
                    cards.add(new Card(elements[0], elements[1]));
                }
                catch(ArrayIndexOutOfBoundsException e){
                    //malformed line; skip it
                }
            }
        }
        catch(IOException io){
            System.out.println("Error importing cards from text! Details: " + io);
            io.printStackTrace();
        }
        finally{
            try{ reader.close(); } catch(IOException io){}
        }
        
        //add cards to project
        project.addCards(cards);
        
        return project;
    }*/
    
    /** Exports the given project's cards as plain text (CSV).
     * 
     * @param project the project to export
     * @param writeTo the file to export the text to
     */
    /*public static void textExport(Project project, File writeTo){
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(writeTo));
            
            for(Card card : project.getCards()){
                //question + delimiter + answer
                writer.write(card.getQuestion() + Card.DELIMITER + card.getAnswer() + Card.NEWLINE);
            }
        }
        catch(IOException io){
            System.out.println("IO Error when exporting project as text. Details: " + io);
            io.printStackTrace();
        }
        finally{
            try{ writer.close(); } catch(IOException io){}
        }
    }*/
	private static File zip(File projectFolder) {try {
		BufferedInputStream origin = null;

		// create a .zip file inside the zipTo folder
		String projectName = projectFolder.getName();
		File zip = new File(projectFolder.getAbsolutePath() + "/"
				+ projectName + "." + Zipper.EXTENSION);

		// does it exist? if so, ask for confimation since we could
		// overwrite
		/*
		 * if(zip.exists()){
		 * if(!InputManager.confirm("<html><center>A file named <i>" +
		 * zip.getAbsolutePath() +
		 * "</i><br />already exists. Do you want to replace it?"
		 * ,gui.getFrame())) return null; }
		 */

		// and have the output stream write to the zip file
		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
				new FileOutputStream(zip)));
		// out.setMethod(ZipOutputStream.DEFLATED);
		byte[] data = new byte[Zipper.BUFFER_SIZE];

		// get a list of files from given folder
		String[] files = projectFolder.list();

		for (int i = 0; i < files.length; i++) {
			File file = new File(files[i]);
			String absolutePath = projectFolder.getAbsolutePath() + "/"
					+ file.getName();
			File newLocation = new File(absolutePath);
			origin = new BufferedInputStream(new FileInputStream(
					newLocation), Zipper.BUFFER_SIZE);
			ZipEntry entry = new ZipEntry(files[i]);
			out.putNextEntry(entry);
			int count;
			while ((count = origin.read(data, 0, Zipper.BUFFER_SIZE)) != -1) {
				out.write(data, 0, count);
			}
			origin.close();
		}
		out.close();
		return zip;
	} catch (Exception e) {
		System.err.println("Error zipping project! Details: ");
		e.printStackTrace();
		return null;
	}} 
	private static void fileZipforAttachment(final File projectFolder) {
		
            	 java.io.File zipped = zip(projectFolder);
            
		
	}
}
