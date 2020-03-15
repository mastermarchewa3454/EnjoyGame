using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;
using System.Text;

public class DataController : MonoBehaviour
{
    string url = "http://13.229.205.106:8080/mathero";
    string userId;
    string authHeader;

    void Update()
    {
        if (Input.GetKeyDown(KeyCode.L))
        {
            StartCoroutine(Login("Booxworm", "p@ssw0rd"));
        }
        else if (Input.GetKeyDown(KeyCode.G))
        {
            StartCoroutine(GetUser());
        }
        else if (Input.GetKeyDown(KeyCode.Space))
        {
            Debug.Log(userId);
            Debug.Log(authHeader);
        }
    }

    IEnumerator Login(string username, string password)
    {
        User user = new User { username = "Booxworm", password = "p@ssw0rd" };
        string loginDetails = JsonUtility.ToJson(user);
        byte[] bodyRaw = Encoding.UTF8.GetBytes(loginDetails);

        var www = new UnityWebRequest(url + "/users/login", "POST");
        www.uploadHandler = (UploadHandler)new UploadHandlerRaw(bodyRaw);
        www.downloadHandler = (DownloadHandler)new DownloadHandlerBuffer();
        www.SetRequestHeader("Content-Type", "application/json");

        yield return www.Send();

        if (www.isNetworkError)
        {
            Debug.Log(www.error);
        }
        else
        {
            Debug.Log("POST success!");
            foreach (KeyValuePair<string, string> dict in www.GetResponseHeaders())
            {
                if (dict.Key == "Authorization")
                    authHeader = dict.Value;
                else if (dict.Key == "UserID")
                    userId = dict.Value;
            }
        }
    }

    IEnumerator GetUser()
    {
        UnityWebRequest www = UnityWebRequest.Get(url + "/users/" + userId);
        www.SetRequestHeader("Authorization", authHeader);
        yield return www.SendWebRequest();

        if (www.isNetworkError || www.isHttpError)
        {
            Debug.Log(www.error);
        }
        else
        {
            // Show results as text
            Debug.Log(www.downloadHandler.text);
        }
    }
}

public class User
{
    public string username;
    public string password;
}
