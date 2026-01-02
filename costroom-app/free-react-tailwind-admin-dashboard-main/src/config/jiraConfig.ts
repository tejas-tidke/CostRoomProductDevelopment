// Jira API Configuration
export const JIRA_CONFIG = {
  // Base URL for your backend API - using relative paths for NGINX reverse proxy
  baseUrl: '',
  
  // API endpoints
  apiEndpoints: {
    recentProjects: '/api/jira/projects/recent',
    allProjects: '/api/jira/projects',
  }
};

// Log configuration for debugging
console.log('Jira Config Loaded:', {
  baseUrl: JIRA_CONFIG.baseUrl,
});

// Get the request URL for backend API
export const getJiraRequestUrl = (endpoint: string) => {
  // Use relative path for NGINX reverse proxy
  return endpoint.startsWith('/') ? endpoint : `/${endpoint}`;
};

// Headers for backend API requests
export const getJiraAuthHeaders = () => {
  return {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  };
};
