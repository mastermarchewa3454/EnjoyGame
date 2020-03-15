using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;
using System.Text;

public class DataController : MonoBehaviour
{
    public static DataController db;

    string url = "http://13.229.205.106:8080/mathero";
    string userId;
    string authHeader;

    void Awake()
    {
        if (db != null)
            Destroy(db);
        else
            db = this;

        DontDestroyOnLoad(this);
    }

    /*void Update()
    {
        if (Input.GetKeyDown(KeyCode.R))
        {
            StartCoroutine(Register("Booxworm",
                                    "Wonn Jen",
                                    "Lee",
                                    "4C",
                                    "sth@gmail.com",
                                    "p@ssw0rd"));
        }
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
            Debug.Log(authHeader);
            Debug.Log(userId);
        }
    }*/

    public IEnumerator Register(string username,
                         string firstName,
                         string lastName,
                         string classId,
                         string email,
                         string password)
    {
        UserRegister user = new UserRegister { username = username,
                                               firstName = firstName,
                                               lastName = lastName,
                                               classId = classId,
                                               email = email,
                                               password = password };
        string userDetails = JsonUtility.ToJson(user);
        yield return StartCoroutine(PostData(url + "/users", userDetails));
        Debug.Log("Registered!");
    }

    public IEnumerator Login(string username, string password)
    {
        UserLogin user = new UserLogin { username = username, password = password };
        string loginDetails = JsonUtility.ToJson(user);
        yield return StartCoroutine(PostData(url + "/users/login", loginDetails, setAuth: true));
        Debug.Log("Logged in!");
    }

    public IEnumerator GetUser()
    {
        if (userId == null)
            Debug.Log("Log in first");
        else
        {
            string userString = "";
            yield return StartCoroutine(GetData(url + "/users/" + userId, callback:data => userString = data));
            Student student = JsonUtility.FromJson<Student>(userString);
            Debug.Log(student.userId);
            Debug.Log(student.username);
            Debug.Log(student.firstName);
            Debug.Log(student.lastName);
            Debug.Log(student.email);
        }
    }

    IEnumerator GetData(string url, System.Action<string> callback = null)
    {
        if (authHeader == null)
            Debug.Log("Log in first");
        else
        {
            UnityWebRequest www = UnityWebRequest.Get(url);
            www.SetRequestHeader("Authorization", authHeader);
            yield return www.SendWebRequest();

            if (www.isNetworkError || www.isHttpError)
            {
                Debug.Log(www.error);
            }
            else
            {
                if (callback != null)
                {
                    callback(www.downloadHandler.text);
                }
            }
        }
    }

    IEnumerator PostData(string url, string body, bool setAuth = false, System.Action<string> callback = null)
    {
        byte[] bodyRaw = Encoding.UTF8.GetBytes(body);
        var www = new UnityWebRequest(url, "POST");
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
                foreach (KeyValuePair<string, string> dict in www.GetResponseHeaders())
                {
                    if (dict.Key == "Authorization")
                        authHeader = dict.Value;
                    else if (dict.Key == "UserID")
                        userId = dict.Value;
                }
            }
            
            if (callback != null)
            {
                callback(www.downloadHandler.text);
            }
            
        }
    }

}

public class UserRegister
{
    public string username;
    public string firstName;
    public string lastName;
    public string classId;
    public string email;
    public string password;
}

public class UserLogin
{
    public string username;
    public string password;
}

public class Student
{
    public string userId;
    public string username;
    public string firstName;
    public string lastName;
    public string email;
}