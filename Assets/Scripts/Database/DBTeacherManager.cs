using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DBTeacherManager : MonoBehaviour
{
    public IEnumerator GetTeacher(System.Action callback)
    {
        string teacherString = "";
        yield return StartCoroutine(GetData("/teachers/" + userId, callback: data => teacherString = data));
        Teacher teacher = JsonUtility.FromJson<Teacher>(teacherString);
    }
}
