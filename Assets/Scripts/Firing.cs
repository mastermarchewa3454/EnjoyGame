using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Firing : MonoBehaviour
{
    public float bulletForce = 10f;

    public Transform firePoint;
    public GameObject projectilePrefab;
    public Rigidbody2D rb;
    public Camera cam;

    Vector2 mousePos;
    float distance = 0.7f;

    // Update is called once per frame
    void Update()
    {
        mousePos = cam.ScreenToWorldPoint(Input.mousePosition);

        if (Input.GetButtonDown("Fire1"))
        {
            Shoot();
        }
    }

    void FixedUpdate()
    {
        Vector2 lookDir = mousePos - rb.position;
        float angle = Mathf.Atan2(lookDir.y, lookDir.x);
        firePoint.position = new Vector2(distance * Mathf.Cos(angle) + rb.position.x,
                                         distance * Mathf.Sin(angle) + rb.position.y);
        firePoint.transform.eulerAngles = new Vector3(firePoint.transform.eulerAngles.x,
                                                      firePoint.transform.eulerAngles.y,
                                                      angle * Mathf.Rad2Deg);
    }

    void Shoot()
    {
        GameObject projectile = Instantiate(projectilePrefab, firePoint.position, firePoint.rotation);
        Rigidbody2D projectileRb = projectile.GetComponent<Rigidbody2D>();
        projectileRb.AddForce(firePoint.right * bulletForce, ForceMode2D.Impulse);
    }
}
