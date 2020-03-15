using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
/// Connection class that will contain the methods/functions to 
/// call the specific function upon clicking on the button
/// </summary>

public class Connection : MonoBehaviour
{
    /// <summary>
    /// Opens up a link to Facebook after clicking on it
    /// </summary>
    public void OpenFacebook()
    {
        Application.OpenURL("https://www.facebook.com/");
    }
    /// <summary>
    /// Opens up a link to Instagram after clicking on it
    /// </summary>
    public void OpenInstagram()
    {
        Application.OpenURL("https://www.instagram.com/");
    }
    /// <summary>
    /// Opens up a link to WhatsApp after clicking on it
    /// </summary>
    public void OpenWhatsApp()
    {
        Application.OpenURL("https://web.whatsapp.com/"); 
    }

}
