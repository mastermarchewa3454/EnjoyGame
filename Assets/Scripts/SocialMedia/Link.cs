using UnityEngine;
using System.Runtime.InteropServices;
using UnityEngine.UI;

public class Link : MonoBehaviour 
{

	public void OpenFacebook()
	{
		#if !UNITY_EDITOR
		openWindow("https://www.facebook.com/");
		#endif
	}


		public void OpenInstagram()
	{
		#if !UNITY_EDITOR
		openWindow("https://www.instagram.com/");
		#endif
	}


		public void OpenWhatsApp()
	{
		#if !UNITY_EDITOR
		openWindow("https://web.whatsapp.com//");
		#endif
	}

	[DllImport("__Internal")]
	private static extern void openWindow(string url);

}