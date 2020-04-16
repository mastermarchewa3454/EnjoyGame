using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;
using System.Text;

public class DBManager : MonoBehaviour
{
    private string url = "http://18.185.65.201:8080/mathero";
    protected string userId;
    protected string authHeader;

    void Awake()
    {
        userId = PlayerPrefs.GetString("userId", null);
        authHeader = PlayerPrefs.GetString("authHeader", null);
    }

    protected IEnumerator GetData(string endpoint, string[] header = null, System.Action<string> callback = null)
    {
        if (authHeader == null)
            Debug.Log("Log in first");
        else
        {
            UnityWebRequest www = UnityWebRequest.Get(url + endpoint);
            www.SetRequestHeader("Authorization", authHeader);
            yield return www.SendWebRequest();

            if (www.isNetworkError || www.isHttpError)
            {
                Debug.Log(www.error);
            }
            else
            {
                if (header != null)
                {
                    foreach (string h in header)
                    {
                        string[] arr = h.Split(':');
                        www.SetRequestHeader(arr[0], arr[1]);
                    }
                }

                if (callback != null)
                    callback(www.downloadHandler.text);
            }
        }
    }

    protected IEnumerator PostData(string endpoint, string body, bool setAuth = false, System.Action<string> callback = null)
    {
        byte[] bodyRaw = Encoding.UTF8.GetBytes(body);
        var www = new UnityWebRequest(url + endpoint, "POST");
        www.uploadHandler = (UploadHandler)new UploadHandlerRaw(bodyRaw);
        www.downloadHandler = (DownloadHandler)new DownloadHandlerBuffer();
        www.SetRequestHeader("Content-Type", "application/json");
        if (authHeader != null)
            www.SetRequestHeader("Authorization", authHeader);

        yield return www.Send();

        if (www.isNetworkError)
        {
            Debug.Log(www.error);
        }
        else
        {
            if (setAuth)
            {
                if (www.responseCode == 200)
                {
                    foreach (KeyValuePair<string, string> dict in www.GetResponseHeaders())
                    {
                        if (dict.Key == "Authorization")
                        {
                            authHeader = dict.Value;
                            PlayerPrefs.SetString("authHeader", authHeader);
                        }
                        else if (dict.Key == "UserID")
                        {
                            userId = dict.Value;
                            PlayerPrefs.SetString("userId", userId);
                        }
                    }
                }
                callback(www.responseCode.ToString());
            }
            else if (callback != null)
            {
                callback(www.downloadHandler.text);
            }
            
        }
    }

}

public static class JsonHelper
{
    public static T[] FromJson<T>(string json)
    {
        Wrapper<T> wrapper = JsonUtility.FromJson<Wrapper<T>>(json);
        Debug.Log(wrapper.wrapperList);
        return wrapper.wrapperList;
    }

    public static string ToJson<T>(T[] array)
    {
        Wrapper<T> wrapper = new Wrapper<T>();
        wrapper.wrapperList = array;
        return JsonUtility.ToJson(wrapper);
    }

    [System.Serializable]
    private class Wrapper<T>
    {
        public T[] wrapperList;
    }
}