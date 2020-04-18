using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;


public class TeacherReportSummary : MonoBehaviour
{
    public Dropdown dropdown;
    public Text selectedName;

    // Start is called before the first frame update
    void Start()
    {
        PopulateList; 
    }

    public void PopulateList()
    {
        List<string> names = new List<string>()
        {

        };
        dropdown.AddOptions(names);
    }
}
