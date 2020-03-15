using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Connection : MonoBehaviour
{
    public void OpenFacebook()
    {
        Application.OpenURL("https://www.facebook.com/");
    }
    public void OpenInstagram()
    {
        Application.OpenURL("https://www.instagram.com/");
    }
    public void OpenWhatsApp()
    {
        Application.OpenURL("https://web.whatsapp.com/"); 
    }

}
