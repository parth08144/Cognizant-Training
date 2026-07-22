import "../Stylesheets/mystyle.css";

function CalculateScore() {

    const Name = "Robert";
    const School = "ABC Public School";
    const Total = 480;
    const Goal = 500;
    const Percentage = (Total / Goal) * 100;

    return (
        <div className="container">

            <h1>Student Details</h1>

            <table>
                <tbody>
                    <tr>
                        <td><b>Name</b></td>
                        <td>{Name}</td>
                    </tr>

                    <tr>
                        <td><b>School</b></td>
                        <td>{School}</td>
                    </tr>

                    <tr>
                        <td><b>Total Marks</b></td>
                        <td>{Total}</td>
                    </tr>

                    <tr>
                        <td><b>Percentage</b></td>
                        <td>{Percentage}%</td>
                    </tr>
                </tbody>
            </table>

        </div>
    );
}

export default CalculateScore;