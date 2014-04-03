import java.util.ArrayList;
import java.util.Locale; 

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;



public class BesiloSWT
{
	private Text questionField;
	
	private List answerField;
	
	private Button answerButton;
	
	private static Canvas animationImage;
	
	private static int bodyOfPlayer = 0;
	
	private static String questionWord = null;
	
	private static String answerWord = null;
	
	private static ArrayList <Character> charOfWord = new ArrayList <Character> ();
	
	private static StringBuilder usedSymbols = new StringBuilder();
	
	
	public static void main (String[] args)
	{
		Locale locale = new Locale("bg", "BG");
		
		Locale.setDefault (locale);
		
		Display display = new Display ();
		
		Shell shell = new BesiloSWT ().ShellOfGame (display);
		
		Rectangle winDisplay = Display.getCurrent ().getBounds ();
		
		int xAxis, yAxis;
		
		xAxis = ( (winDisplay.width / 2) - ( (shell.getBounds ().width) / 2));
		yAxis = ( (winDisplay.height / 2) - ( (shell.getBounds ().height) / 2));
		
		shell.setLocation (xAxis, yAxis);
		
		shell.open ();
		
		while (!shell.isDisposed ())
		{
			if (!display.readAndDispatch ())
				
				display.sleep ();
		}// end while
		shell.dispose ();
	}// close main
	
	
	private Shell ShellOfGame (final Display display)
	{
		final Shell shell = new Shell (display, SWT.CLOSE);
		
		shell.setText ("Игра Бесилка");
		
		GridLayout gridLayout = new GridLayout ();
		
		gridLayout.numColumns = 3;
		
		shell.setLayout (gridLayout);
		
		
		new Label (shell, SWT.NONE).setText ("Въведете (дума/буква):");
		
		questionField = new Text (shell, SWT.SINGLE | SWT.SIMPLE);
		
		GridData gridData = new GridData (GridData.FILL, GridData.CENTER, true, false);
		
		gridData.horizontalSpan = 2;
		
		questionField.setLayoutData (gridData);
		
		
		Label label = new Label (shell, SWT.BEGINNING);
		
		label.setText ("Отговор: ");
		
		label.setLayoutData (new GridData (GridData.FILL, GridData.CENTER, true, false));
		
		answerField = new List (shell, SWT.SINGLE | SWT.SIMPLE);
		
		answerField.getItemHeight ();
		
		answerField.setLayoutData (gridData);
		
		
		final Button submit = new Button (shell, SWT.PUSH);
		
		submit.setText ("Въведи дума");
		
		gridData = new GridData (GridData.FILL, GridData.CENTER, true, false);
		
		submit.setLayoutData (gridData);
		
		submit.addSelectionListener (new SelectionListener ()
		{
			
			public void widgetSelected (SelectionEvent se)
			{	
				try
                {
	                questionWord = questionField.getText ();							
	                	
	                questionField.setText ("");		

	                if ((questionWord != null) && (questionWord != ""))
                    {
	                    ArrayList <Character> charOfWordOk = new ArrayList <Character> ();
	                    
	                    for (int i = 0; i < (questionWord.length ()); i++)
	                    {
		                    charOfWord.add (i, (questionWord.charAt (i)));
		                    
		                    charOfWordOk.add ('*');
	                    }// end for
	                    answerWord = CharOfWordToDuma (charOfWord);
	                    
	                    String word = CharOfWordToDuma (charOfWordOk);
	                    
	                    answerField.add (word, 0);
	                    
	                    charOfWord = charOfWordOk;
	                    
	                    answerWord = null;
	                    
	                    questionField.setFocus ();
	                    
	                    submit.setEnabled (false);
	                    
	                    answerButton.setEnabled (true);
                    }// end if
	                else
	                {
	                	questionField.setFocus ();
	                }// end else
                }
                catch (Exception e)
                {
                	questionField.setFocus ();
                	
                }// end catch
				
			}// close widgetSelected()
		
		
			
			public void widgetDefaultSelected (SelectionEvent arg0)
			{
				
			}
		});
		
		
		new Label (shell, SWT.NONE).setText ("Бесилка: ");
		
		animationImage = new Canvas (shell, SWT.BORDER);
		
		gridData = new GridData (GridData.FILL, GridData.FILL, true, true);
		
		gridData.widthHint = 200;
		
		gridData.heightHint = 100;
		
		gridData.verticalSpan = 3;
		
		animationImage.setLayoutData (gridData);
		
		animationImage.addPaintListener (new AnimationImagePaintListener ());
		
		
		answerButton = new Button (shell, SWT.PUSH);
		
		answerButton.setText ("Въведи буква");
		
		gridData = new GridData (GridData.FILL, GridData.CENTER, true, false);
		
		answerButton.setLayoutData (gridData);
		
		answerButton.setEnabled (false);
		
		
		answerButton.addSelectionListener (new SelectionListener ()
		{
			public void widgetDefaultSelected (SelectionEvent se)
			{
				
			}
			
			
			
            public void widgetSelected (SelectionEvent se)
			{				
				try
                {
	                String symbol = null;
	                
	                boolean result = false;
	                boolean isUsed = false;
	                
	                symbol = questionField.getText (); 
	                	                	                
	                for (int index = 0; index < (usedSymbols.length ()); index++)
	                {
		                if (usedSymbols.charAt (index) == symbol.charAt (0))
		                {		                
		                	isUsed = true;
		                	break;
		                }
	                }
	                
	                if(isUsed == false)
	                {
	                	usedSymbols.append (symbol.charAt (0));
	                }
	                
	                for (int i = 0; i < (questionWord.length ()); i++)
	                {
	                	if (questionWord.charAt (i) == symbol.charAt (0))
	                	{
	                		
	                		charOfWord.set (i, (questionWord.charAt (i)));
	                		
	                		result = true;
	                	}// end if
	                }// close for loop
	                
	                answerWord = CharOfWordToDuma (charOfWord);
	                
	                answerField.removeAll ();
	                
	                answerField.add (answerWord, 0);
	                
	                if(isUsed == false)
	                {	                	
		                if (result == false)
		                {
		                	bodyOfPlayer++;
		                	
		                	animationImage.redraw ();
		                	
		                	if (bodyOfPlayer == 7)
		                	{
		                		answerButton.setEnabled (false);
		                	}// end inner if
		                }// end if
		                else if (questionWord.equals (answerWord))
		                {	
		                	answerField.add (answerWord, 0);
		                	
		                	answerButton.setEnabled (false);
		                }// end else if
	                }// end if(isUsed)
	                
	                questionField.setText ("");
	                
	                questionField.setFocus ();
                }
                catch (Exception e)
                {
	                questionField.setFocus ();
                }			
			}// widgetSelected()
			
		});
		
		
		shell.pack ();
		
		return shell;
	}// close ShellOfGame
	
	
	private String CharOfWordToDuma (ArrayList <Character> ch)
	{
		StringBuilder stringBuilder = new StringBuilder();		
		
		for (int i = 0; i < charOfWord.size (); i++)
		{
			stringBuilder.append (ch.get (i));
		}// close for loop
		return stringBuilder.toString ();
	}// close dToanswerWord ()
	
	
	private class AnimationImagePaintListener implements PaintListener
	{
		public void paintControl (PaintEvent e)
		{
			
			Draw (e);
			
			e.gc.dispose ();
		}// close paintControl()
	}// AnimationImagePaintListener
	
	
	private void Draw (PaintEvent e)
	{
		switch (bodyOfPlayer)
		{
			case 7:
			{
				e.gc.drawLine (83, 120, 90, 150);// right leg		
			}
			
			case 6:
			{
				e.gc.drawLine (77, 120, 70, 150);// left leg
			}
				
			case 5:
			{
				e.gc.drawLine (83, 70, 110, 90);// right hand
			}
				
			case 4:
			{
				e.gc.drawLine (77, 70, 50, 90);// left hand				
			}
				
			case 3:
			{
				e.gc.drawOval (65, 70, 30, 50);// body				
			}
				
			case 2:
			{				
				e.gc.drawOval (70, 50, 20, 20);// head				
			}
				
			case 1:
			{				
				e.gc.drawLine (80, 15, 80, 50);// rope	
			}
			
			default:
			{
				e.gc.drawPolygon(new int[] { 80, 15, 160, 15, 160, 185, 150, 185, 150, 25, 80, 25});
			}				
		}// close switch
			
	}// close drow()
	
}
