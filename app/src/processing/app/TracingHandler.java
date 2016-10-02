package processing.app;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.text.BadLocationException;

public class TracingHandler {
	
	private Editor editor;
	
	public TracingHandler(Editor editor) {
		this.editor = editor;
	}
	
	public void selectLine(String filePath, int line){
		//if file is open, activate him
		File file = new File(filePath);
		if(!file.exists()){
			System.out.println("Debug error: wrong file to open - " + filePath + "\n");
			return;
		}
		try {
			SketchFile existFile = isFileOpen(file.getName()); 
			if(existFile != null)
				editor.selectTab(editor.findTabIndex(existFile));
			else
				editor.addTab(new SketchFile(editor.sketch, file), null);
			//select line
			editor.getCurrentTab().getTextArea().addLineHighlight(line-1, Color.YELLOW);
			editor.getCurrentTab().getTextArea().setCaretPosition(editor.getCurrentTab().getTextArea().getLineStartOffset(line-1));
		} catch (BadLocationException e) {
			System.out.println("Debug error: wrong line number - " + line + "\n");
		} catch (IOException e) {
			System.out.println("Debug error: wrong file name - " + file.getName() + "\n");
		}
	}
	
	public void deselectAllLines(){
		editor.removeAllLineHighlights();
	}
	
	private SketchFile isFileOpen(String name){
		List<EditorTab> tabs = editor.getTabs();
		for(EditorTab tab : tabs){
			if(tab.file.getFileName().equals(name))
				return tab.file;
		}
		return null;

	}
}
