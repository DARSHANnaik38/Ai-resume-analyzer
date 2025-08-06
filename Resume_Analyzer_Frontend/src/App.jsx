import { useState } from "react";
import axios from "axios";
import "./App.css"; // Make sure you have your custom CSS file

function App() {
  const [appState, setAppState] = useState("upload");
  const [skills, setSkills] = useState([]);
  const [jobs, setJobs] = useState([]);
  const [fileName, setFileName] = useState("");

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      setFileName(file.name);
      startAnalysis(file);
    }
  };

  const startAnalysis = async (file) => {
    setAppState("analyzing");

    try {
      const formData = new FormData();
      formData.append("file", file);

      const skillsResponse = await axios.post(
        "http://localhost:8080/api/resume/extract-skills",
        formData
      );
      const extractedSkills = skillsResponse.data;
      setSkills(extractedSkills);

      const jobsResponse = await axios.post(
        "http://localhost:8080/api/jobs/search",
        {
          skills: extractedSkills,
        }
      );

      // **FIXED LINE**
      // axios automatically parses the JSON, so we can use jobsResponse.data directly.
      const jobsData = jobsResponse.data;

      // Adzuna nests the jobs in a "results" array.
      setJobs(jobsData.results || []);

      setAppState("results");
    } catch (error) {
      console.error("Error during analysis:", error);
      setAppState("error");
    }
  };

  const handleAnalyzeAnother = () => {
    setAppState("upload");
    setFileName("");
    setSkills([]);
    setJobs([]);
  };

  const renderContent = () => {
    switch (appState) {
      case "analyzing":
        return (
          <div className="analyzing-view">
            <div className="loader"></div>
            <h2 className="main-heading gradient-text">Analyzing...</h2>
            <p className="sub-heading">
              Extracting skills and finding opportunities.
            </p>
          </div>
        );
      case "results":
        return (
          <div className="results-view">
            <h2
              className="main-heading"
              style={{ textAlign: "center", marginBottom: "2.5rem" }}
            >
              Your Personalized <span className="gradient-text">Analysis</span>
            </h2>
            <div className="results-grid">
              <div className="glass-card">
                <h3 className="card-title">Extracted Skills</h3>
                <div className="skills-container">
                  {skills.length > 0 ? (
                    skills.map((skill) => (
                      <span key={skill} className="skill-tag">
                        {skill}
                      </span>
                    ))
                  ) : (
                    <p>No specific skills found.</p>
                  )}
                </div>
              </div>
              <div className="glass-card">
                <h3 className="card-title">Top Job Matches</h3>
                <div className="jobs-container">
                  {jobs.length > 0 ? (
                    jobs.map((job, index) => (
                      <div key={index} className="job-card">
                        <h4
                          style={{
                            fontWeight: "700",
                            fontSize: "1.125rem",
                            color: "white",
                          }}
                        >
                          {job.title || "N/A"}
                        </h4>
                        <p style={{ color: "#9ca3af" }}>
                          {job.company?.display_name || "N/A"}
                        </p>
                        <p
                          style={{
                            color: "#6b7280",
                            fontSize: "0.875rem",
                            marginTop: "0.25rem",
                          }}
                        >
                          {job.location?.display_name || "N/A"}
                        </p>
                      </div>
                    ))
                  ) : (
                    <p>No direct job matches found.</p>
                  )}
                </div>
              </div>
            </div>
            <div style={{ textAlign: "center", marginTop: "2rem" }}>
              <button
                onClick={handleAnalyzeAnother}
                className="secondary-button"
              >
                Analyze Another Resume
              </button>
            </div>
          </div>
        );
      case "error":
        return (
          <div className="error-view">
            <p
              style={{
                color: "#f87171",
                fontWeight: "700",
                fontSize: "1.5rem",
              }}
            >
              Error Analyzing Resume
            </p>
            <p className="sub-heading">
              Please ensure the backend server is running and try again.
            </p>
            <button
              onClick={handleAnalyzeAnother}
              className="secondary-button"
              style={{ marginTop: "2rem" }}
            >
              Try Again
            </button>
          </div>
        );
      case "upload":
      default:
        return (
          <div className="upload-view">
            <h1 className="main-heading">
              Unlock Your{" "}
              <span className="gradient-text">Career Potential</span>
            </h1>
            <p className="sub-heading">
              Our AI analyzes your resume to extract key skills and match you
              with the perfect job opportunities. Get started by uploading your
              resume.
            </p>
            <div>
              <label htmlFor="resume-upload" className="upload-button">
                Upload Your Resume
              </label>
              <input
                type="file"
                id="resume-upload"
                style={{ display: "none" }}
                accept=".pdf,.doc,.docx,.txt"
                onChange={handleFileChange}
              />
            </div>
            {fileName && (
              <p style={{ marginTop: "1rem", color: "#6b7280" }}>{fileName}</p>
            )}
          </div>
        );
    }
  };

  return <div className="main-container">{renderContent()}</div>;
}

export default App;
