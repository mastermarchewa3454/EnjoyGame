using UnityEngine;
using System.Collections;
using UnityEngine.UI;
 
public class ButtonCounter : MonoBehaviour 
{
    public Text counterText; 
    public int counterValue=1;
    public void counter()
    {
        if(counterValue == 20)
        {
            return;
        }
        else 
        {
            counterValue++;  
            counterText.text = counterValue.ToString() + "/20";
        }
    }

}